package com.citta.lucidkanban.model;

import java.io.Serializable;

public class Member implements Serializable {
    public String memberName;
    public String memberEmail;
    public String memberEmployeeId;
    public int memberImage;

    public Member(
            String memberName,
            String memberEmail,
            String memberEmployeeId,
            int memberImage
    )
    {
        this.memberName = memberName;
        this.memberEmail = memberEmail;
        this.memberEmployeeId = memberEmployeeId;
        this.memberImage = memberImage;
    }
}
