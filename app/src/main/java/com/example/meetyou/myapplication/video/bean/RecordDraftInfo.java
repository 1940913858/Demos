package com.example.meetyou.myapplication.video.bean;

import com.tencent.ugc.TXRecordCommon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinsonswang on 2018/9/4.
 */

public class RecordDraftInfo {
    // 您还可以在草稿箱中保存该分段设置的、美颜、大眼等效果值，按需完善
    private int aspectRatio; // 屏比
    // 草稿箱分段的路径
    private List<RecordPart> partList;

    public RecordDraftInfo(){
        aspectRatio = TXRecordCommon.VIDEO_ASPECT_RATIO_9_16; // 视频比例
        partList = new ArrayList<>();
    }

    public int getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(int aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public List<RecordPart> getPartList() {
        return partList;
    }

    public void setPartList(List<RecordPart> partList) {
        this.partList = partList;
    }

    public class RecordPart{
        private String path;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

    }
}
