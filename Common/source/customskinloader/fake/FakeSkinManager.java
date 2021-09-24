package customskinloader.fake;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import customskinloader.CustomSkinLoader;
import customskinloader.fake.itf.IFakeMinecraft;
import customskinloader.fake.itf.IFakeTextureManager_1;
import customskinloader.fake.itf.IFakeTextureManager_2;
import customskinloader.utils.HttpTextureUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.util.ResourceLocation;

public class FakeSkinManager {
    private static final ExecutorService THREAD_POOL = new ThreadPoolExecutor(CustomSkinLoader.config.threadPoolSize, CustomSkinLoader.config.threadPoolSize, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue<>());
    private final TextureManager textureManager;

    private final Map<ResourceLocation, MinecraftProfileTexture> modelCache = new HashMap<>();

    public FakeSkinManager(TextureManager textureManagerInstance, File skinCacheDirectory, MinecraftSessionService sessionService) {
        this.textureManager = textureManagerInstance;
        HttpTextureUtil.defaultCacheDir = skinCacheDirectory;
    }

    public ResourceLocation loadSkin(MinecraftProfileTexture profileTexture, MinecraftProfileTexture.Type textureType) {
        return this.loadSkin(profileTexture, textureType, null);
    }

    public ResourceLocation loadSkin(final MinecraftProfileTexture profileTexture, final MinecraftProfileTexture.Type textureType, final SkinManager.SkinAvailableCallback skinAvailableCallback) {
        return this.loadSkin(profileTexture, HttpTextureUtil.toHttpTextureInfo(profileTexture.getUrl()), textureType, skinAvailableCallback);
    }

    private ResourceLocation loadSkin(final MinecraftProfileTexture profileTexture, final HttpTextureUtil.HttpTextureInfo info, final MinecraftProfileTexture.Type textureType, final SkinManager.SkinAvailableCallback skinAvailableCallback) {
        final ResourceLocation resourcelocation = new ResourceLocation("skins/" + Hashing.sha1().hashUnencodedChars(info.hash).toString());

        if (((IFakeTextureManager_1) this.textureManager).getTexture(resourcelocation, null) != null) {//Have already loaded
            makeCallback(skinAvailableCallback, textureType, resourcelocation, modelCache.getOrDefault(resourcelocation, profileTexture));
        } else {
            SimpleTexture threaddownloadimagedata = FakeThreadDownloadImageData.createThreadDownloadImageData(info.cacheFile, info.url, DefaultPlayerSkin.getDefaultSkinLegacy(), new BaseBuffer(skinAvailableCallback, textureType, resourcelocation, profileTexture), textureType);
            if (skinAvailableCallback instanceof FakeClientPlayer.LegacyBuffer)//Cache for client player
                FakeClientPlayer.textureCache.put(resourcelocation, threaddownloadimagedata);
            ((IFakeTextureManager_2) this.textureManager).func_229263_a_(resourcelocation, (Texture) threaddownloadimagedata);
        }
        return resourcelocation;
    }

    public void loadProfileTextures(final GameProfile profile, final SkinManager.SkinAvailableCallback skinAvailableCallback, final boolean requireSecure) {
        THREAD_POOL.execute(() -> CustomSkinLoader.loadProfileLazily(profile, m -> {
            final Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = Maps.newHashMap();
            map.putAll(m);

            for (MinecraftProfileTexture.Type type : MinecraftProfileTexture.Type.values()) {
                MinecraftProfileTexture profileTexture = map.get(type);
                if (profileTexture != null) {
                    HttpTextureUtil.HttpTextureInfo info = HttpTextureUtil.toHttpTextureInfo(profileTexture.getUrl());
                    FakeThreadDownloadImageData.downloadTexture(info.cacheFile, info.url);

                    ((IFakeMinecraft) Minecraft.getMinecraft()).execute(() -> {
                        CustomSkinLoader.logger.debug("Loading type: " + type);
                        try {
                            this.loadSkin(profileTexture, info, type, skinAvailableCallback);
                        } catch (Throwable t) {
                            CustomSkinLoader.logger.warning(t);
                        }
                    });
                }
            }
        }));
    }

    public Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> loadSkinFromCache(GameProfile profile) {
        return CustomSkinLoader.loadProfileFromCache(profile);
    }

    private static void makeCallback(SkinManager.SkinAvailableCallback callback, MinecraftProfileTexture.Type type, ResourceLocation location, MinecraftProfileTexture texture) {
        if (callback != null)
            callback.skinAvailable(type, location, texture);
    }

    private class BaseBuffer implements IImageBuffer {
        private IImageBuffer buffer;

        private SkinManager.SkinAvailableCallback callback;
        private MinecraftProfileTexture.Type type;
        private ResourceLocation location;
        private MinecraftProfileTexture texture;

        public BaseBuffer(SkinManager.SkinAvailableCallback callback, MinecraftProfileTexture.Type type, ResourceLocation location, MinecraftProfileTexture texture) {
            switch (type) {
                case SKIN: this.buffer = new FakeSkinBuffer(); break;
                case CAPE: this.buffer = new FakeCapeBuffer(location); break;
            }

            this.callback = callback;
            this.type = type;
            this.location = location;
            this.texture = texture;
        }

        public net.minecraft.client.renderer.texture.NativeImage func_195786_a(net.minecraft.client.renderer.texture.NativeImage image) {
            return buffer instanceof FakeSkinBuffer ? ((FakeSkinBuffer) buffer).func_195786_a(image) : image;
        }

        public BufferedImage parseUserSkin(BufferedImage image) {
            return buffer instanceof FakeSkinBuffer ? ((FakeSkinBuffer) buffer).parseUserSkin(image) : image;
        }

        public void skinAvailable() {
            if (buffer != null) {
                buffer.skinAvailable();
                if ("auto".equals(texture.getMetadata("model")) && buffer instanceof FakeSkinBuffer) {
                    //Auto judge skin type
                    Map<String, String> metadata = Maps.newHashMap();
                    String type = ((FakeSkinBuffer) buffer).judgeType();
                    metadata.put("model", type);
                    texture = new MinecraftProfileTexture(texture.getUrl(), metadata);
                    FakeSkinManager.this.modelCache.put(location, texture);
                }
            }

            FakeSkinManager.makeCallback(callback, type, location, texture);
        }
    }
}
