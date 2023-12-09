package com.raftec.palabrita.vocabularyservice;

import java.sql.Timestamp;

public final class TestConstants
{
    public static final  String UserId1 = "test|b8ba46db28abfa56bfaed887"; // User owns collections 1, 2, 5
    public static final  String UserId2 = "test|d4227ab2523cc6154a74a0fb "; // User owns collections 3, 4
    public static final  String UserId3 = "test|7e7e7f367447efa43f1e1ffe "; // User owns no collections

    public static final  String CollectionId1 = "4f9a5093"; // Owned by UserId1
    public static final  String CollectionId2 = "8b69a73d"; // Owned by UserId1
    public static final String CollectionId3 = "ef3babca"; // Owned by UserId2
    public static final String CollectionId4 = "bb0d9157"; // Owned by UserId2
    public static final  String CollectionId5 = "b123e4de"; // Owned by UserId1

    public static final String CollectionId6 = "0018a71e"; // Unknown collection used for testing

    // yyyy-mm-dd hh:mm:ss
    public static final Timestamp CreationDate1 = Timestamp.valueOf("2021-10-22 23:09:45");
    public static final Timestamp CreationDate2 = Timestamp.valueOf("2023-10-22 23:09:45");

    public static final String CollectionName1 = "Test Collection 1";
}