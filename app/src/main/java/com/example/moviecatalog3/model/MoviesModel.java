package com.example.moviecatalog3.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "tb_movie")
public class MoviesModel implements Parcelable {

    @PrimaryKey()
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private int id;

    @ColumnInfo(name ="original_tittle" )
    @SerializedName("original_title")
    private String title;

    @ColumnInfo(name ="original_name" )
    @SerializedName("original_name")
    private String tv_title;
    @ColumnInfo(name ="original_date" )
    @SerializedName("release_date")
    private String date;
    @ColumnInfo(name ="original_tvdate" )
    @SerializedName("first_air_date")
    private String tv_date;
    @ColumnInfo(name ="original_description" )
    @SerializedName("overview")
    private String description;
    @ColumnInfo(name ="original_img" )
    @SerializedName("poster_path")
    private String img;
    @ColumnInfo(name ="original_lang" )
    @SerializedName("original_language")
    private String lang;

    @ColumnInfo(name = "type")
    private String type;

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String getTv_title() {
        return tv_title;
    }

    public String getImg() {
        return img;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getLang() {
        return lang;
    }

    public String getTv_date() {
        return tv_date;
    }

    public MoviesModel(int id, String title, String img, String date, String description, String lang, String tv_title,String tv_date) {
       this.id =id;
        this.title = title;
        this.date = date;
        this.description = description;
        this.img = img;
        this.lang = lang;
        this.tv_title = tv_title;
        this.tv_date = tv_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.date);
        dest.writeString(this.description);
        dest.writeString(this.img);
        dest.writeString(this.lang);
        dest.writeString(this.tv_title);
        dest.writeString(this.tv_date);
        dest.writeString(this.type);
    }

    protected MoviesModel(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.date = in.readString();
        this.description = in.readString();
        this.img = in.readString();
        this.lang = in.readString();
        this.tv_title = in.readString();
        this.tv_date = in.readString();
        this.type = in.readString();
    }


    public static final Parcelable.Creator<MoviesModel> CREATOR = new Parcelable.Creator<MoviesModel>() {
        @Override
        public MoviesModel createFromParcel(Parcel source) {
            return new MoviesModel(source);
        }

        @Override
        public MoviesModel[] newArray(int size) {
            return new MoviesModel[size];
        }
    };
}
