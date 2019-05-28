package com.citta.lucidkanban.Model;

import android.media.Image;

public class Member {
    private String memberName;
    private String memberEmail;
    private String memberEmployeeId;
    private Image memberImage;

    public Member(String memberName, String memberEmail, String memberEmployeeId, Image memberImage)
    {
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.memberEmployeeId = memberEmployeeId;
        this.memberImage = memberImage;
    }
}
