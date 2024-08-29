package com.gyw.resource;

import com.gyw.entity.FunctionItem;

import java.util.ArrayList;
import java.util.List;

public class FunctionItemStatic {
    public static List<FunctionItem> getFunctionItem(){
        List<FunctionItem> data = new ArrayList<>();
        data.add(new FunctionItem("1","轻松管理音乐库：维塔斯支持扫描本地存储的音乐文件，自动构建音乐库，您可以轻松浏览和管理您的音乐收藏。"));
        data.add(new FunctionItem("2","个性化体验：维塔斯提供了多种主题和颜色选择，让您可以根据自己的喜好进行个性化定制，让音乐播放器与您的心情和风格相匹配。"));
        data.add(new FunctionItem("3","畅享高品质音乐：无论是MP3、WAV还是FLAC等多种音频格式，维塔斯都能完美支持，让您尽情享受音乐的美妙。"));
        data.add(new FunctionItem("4","便捷的播放控制：维塔斯提供了全面的播放控制功能，包括播放、暂停、上一曲、下一曲等，让您随心切换和控制音乐播放。"));
        data.add(new FunctionItem("5","个性化播放列表：您可以根据自己的喜好创建和管理播放列表，让您随时随地都能听到您最喜爱的音乐。"));
        data.add(new FunctionItem("6","与朋友分享：维塔斯支持将您喜爱的音乐分享到社交媒体平台，让您可以与朋友分享您的音乐发现和心情。"));
        data.add(new FunctionItem("7","更多功能不断更新：我们致力于不断改进和更新维塔斯，为您提供更多更优质的音乐体验，让您的音乐旅程更加丰富和精彩。"));
        return data;
    }
}
