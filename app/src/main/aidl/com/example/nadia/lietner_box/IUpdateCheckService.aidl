// IUpdateCheckService.aidl
package com.example.nadia.lietner_box;

// Declare any non-default types here with import statements

interface IUpdateCheckService {
    long getVersionCode(String packageName);
}
