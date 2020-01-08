package com.example.meetyou.myapplication.bean;

import android.os.Parcel;
import android.os.Parcelable;


import java.io.Serializable;

/**
 * Created with IntelliJ IDEA. R
 * Date: 14-7-14
 */
public class PhotoModel implements Serializable, Parcelable {

    /**
     *
     */
    private static final long serialVersionUID = 3704947381379661036L;
    public long Id;
    public long BucketId;
    public boolean IsRecent = false;
    public String DisplayName;

    public String Url;  //原图路径
    public String UrlThumbnail; //压缩图路径
    public String compressPath;
    public boolean isTakePhoto = false;//是否是拍照

    /**
     * 是否是拍照项。
     */
    private boolean isTakePhotoItem;

    private long time;
    private int section;

    private boolean hasUp = false;

    private int indexPosition;

    public int getIndexPosition() {
        return indexPosition;
    }

    public void setIndexPosition(int indexPosition) {
        this.indexPosition = indexPosition;
    }

    public boolean getHasUp() {
        return hasUp;
    }

    public void setHasUp(boolean hasUp) {
        this.hasUp = hasUp;

    }

    private int status = -1;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;

    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        //修正VIVO，移动相册，time会大于Integer.max；
        String timeStr = String.valueOf(time);
        if (timeStr.length() > 10) {
            timeStr = timeStr.substring(0, 10);
            time = Integer.parseInt(timeStr);
        }

        this.time = time;
    }


    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public PhotoModel() {

    }

    public PhotoModel(long id, long bucketId, String displayName, String url, boolean recent,
                      String urlThumbnail) {
        Id = id;
        BucketId = bucketId;
        DisplayName = displayName;
        Url = url;
        IsRecent = recent;
        UrlThumbnail = urlThumbnail;
    }

    /**
     * 判断是否是拍照项。
     *
     * @return true：是；false：否。
     */
    public boolean isTakePhotoItem() {
        return isTakePhotoItem;
    }

    /**
     * 设置是否是拍照项。
     *
     * @param takePhotoItem true：是；false：否。
     */
    public void setTakePhotoItem(boolean takePhotoItem) {
        isTakePhotoItem = takePhotoItem;
    }

    @Override
    public String toString() {
        return "PhotoModel{" +
                "Id=" + Id +
                ", BucketId=" + BucketId +
                ", IsRecent=" + IsRecent +
                ", DisplayName='" + DisplayName + '\'' +
                ", Url='" + Url + '\'' +
                ", UrlThumbnail='" + UrlThumbnail + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (o instanceof PhotoModel) {
            PhotoModel model = (PhotoModel) o;
            return Id == model.Id && BucketId == model.BucketId;
        } else {
            return false;
        }
    }

    protected PhotoModel(Parcel in) {
        this.Id = in.readLong();
        this.BucketId = in.readLong();
        this.IsRecent = in.readByte() != 0;
        this.DisplayName = in.readString();
        this.Url = in.readString();
        this.UrlThumbnail = in.readString();
        this.compressPath = in.readString();
        this.isTakePhoto = in.readByte() != 0;
    }

    public static final Creator<PhotoModel> CREATOR = new Creator<PhotoModel>() {
        @Override
        public PhotoModel createFromParcel(Parcel in) {
            return new PhotoModel(in);
        }

        @Override
        public PhotoModel[] newArray(int size) {
            return new PhotoModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.Id);
        dest.writeLong(this.BucketId);
        dest.writeByte((byte) (this.IsRecent ? 1 : 0));
        dest.writeString(this.DisplayName);
        dest.writeString(this.Url);
        dest.writeString(this.UrlThumbnail);
        dest.writeString(this.compressPath);
        dest.writeByte((byte) (this.isTakePhoto ? 1 : 0));
    }
}
