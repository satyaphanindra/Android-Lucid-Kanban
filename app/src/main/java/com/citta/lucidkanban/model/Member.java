package com.citta.lucidkanban.model;

public class Member {
    private String memberName;
    private String memberEmail;
    private String memberEmployeeId;
    private int memberImage;

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
