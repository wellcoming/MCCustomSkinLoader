# CustomSkinLoader  
## What's this?  
Custom Skin Loader mod for Minecraft.  
It's a mod which can load skins and capes from any online source or from your local.
  
## Download  
### Release Build
- [CurseForge](https://minecraft.curseforge.com/projects/customskinloader)
- [Modrinth](https://modrinth.com/mod/customskinloader)

### Develop Build
- [GitHub Action](https://github.com/xfl03/MCCustomSkinLoader/actions)
- [33 Kit（Chinese/中文）](https://3-3.dev/csl-download)

## Contact Us
- [Telegram @customskinloader](https://t.me/customskinloader)
- [QQ Group（Chinese/中文） 651287593](https://jq.qq.com/?_wv=1027&k=vF16R5tg)

## Feature  
### Support Plenty of Skin Load API and Customizable Skin Load List  
Supported skin loading APIs:
- [MojangAPI](http://wiki.vg/Mojang_API)
- [CustomSkinAPI](https://github.com/xfl03/CustomSkinLoaderAPI/tree/master/CustomSkinAPI)  
- CustomSkinAPIPlus (Test Only)  
- [UniSkinAPI](https://github.com/RecursiveG/UniSkinServer/tree/master/doc)
- [ElyByAPI](https://docs.ely.by/en/api.html) 
- Legacy  

Supported special skin sites:
- **ANY SITE** implements skin loading API above
- [Glitchless](https://games.glitchless.ru/games/minecraft/)
- [MinecraftCapes](https://minecraftcapes.net/)

You can use this feature to customize your skin load list so as to load your skins from any skin server you want.  
If you are the owner of skin server, you can use CustomSkinLoader to load skins from your server if one of the APIs has been actualized by your server.  
  
### HD Skins Support  
Even though you don't have OptiFine and MCPatcher, CustomSkinLoader can still load and render HD skins.  
You can easily to get a better view of skins.  
  
### Skull Support  
Fixed skull loading bug, you can apply any skin to your skull now.  
Dynamic skull supported.  
  
### Profile Cache  
- Decrease the frenquency of using the network.  
- Meanwhile, you can still load profiles when network is unavailable.(*)  

*Only when it is opened in configratulation.  
  
### Local Skin  
Load skins without a skin server.  
Furthermore, by using this function you can preview your skins in game and even change the default skin and model.  
You can load local skins by using any API (excluding MojangAPI).  
*While using default configratulation, just put your skins into `.minecraft/CustomSkinLoader/LocalSkin/(skin|cape)s/{USERNAME}.png`.  
   
### Extra List  
A json file generated by skin servers which supports this feature.  
To add a server to your load list, users just need to put the file into `.minecraft/CustomSkinLoader/ExtraList` .  
  
### Transparent Skin Support  
The problem of incorrectly rendering textures has been fixed.  
  
### Spectator Menu Fixed  
By using this mod, you can see correct avatar of players in Spectator Menu rather than steve and alex.  
  
## Default Load List  
- [Mojang](http://www.minecraft.net/) (MojangAPI)
- [LittleSkin](https://littleskin.cn/) (CustomSkinAPI)  
- [Blessing Skin](http://skin.prinzeugen.net/) (CustomSkinAPI)
- [ElyBy](http://docs.ely.by/) (ElyByAPI)
- SkinMe (UniSkinAPI)
- [TLauncher](https://tlauncher.org/) (ElyByAPI)
- LocalSkin (Legacy)
- [Glitchless](https://games.glitchless.ru/games/minecraft/)
- [MinecraftCapes](https://minecraftcapes.net/)

If you want to apply to add other skin server to default list, please go to [issue](https://github.com/JLChnToZ/MCCustomSkinLoader/issues).  
  
## To Skin Server Owner  
CustomSkinLoader is designed for loading from any server, which makes the mod complex.  
It's not a good idea to refer to CustomSkinLoader's source code to develop your own skin mod.  
It's recommended to use CustomSkinLoader for your server directly.  
Furthermore, you can add your server to 'Default Load List'.  
You can also use 'ExtraList' which makes it easier for users to add your server into load list.  

## Development and Contribution
See [CONTRIBUTING.md](CONTRIBUTING.md)

## Copyright & LICENSE  
### Major Contributor
- 2013-2014 Jeremy Lam([JLChnToZ](https://github.com/JLChnToZ))
- 2014-2023 Alexander Xia([xfl03](https://github.com/xfl03))
- 2020-2023 [ZekerZhayard](https://github.com/ZekerZhayard)

### Binary File  
You could not modify binary file.  
Feel free to use and share this mod and unmodified file in anyway like modpack.  
When using in modpack, you must put 'CustomSkinLoader' in mod list.   
You could not repost this mod to any website without permission.  
You could not earn money with this mod excluding modpack.  

### Source Code  
#### Package 'customskinloader'  
Including some codes from 
- [AsteriskTeam/TabIconHackForge](https://gitee.com/AsteriskTeam/TabIconHackForge) (GPLv3)
- [RecursiveG/UniSkinMod](https://github.com/RecursiveG/UniSkinMod) (GPLv3)  
- [NekoCaffeine/Alchemy](https://github.com/NekoCaffeine/Alchemy) (GPLv3)  

```
This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
```
GPLv3: http://www.gnu.org/licenses/gpl.html  
  
You should change the name of the package to avoid others' misunderstanding.  
