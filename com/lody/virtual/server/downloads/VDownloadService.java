package com.lody.virtual.server.downloads;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import com.lody.virtual.client.core.VirtualCore;
import com.lody.virtual.helper.utils.VLog;

public class VDownloadService {
  private ContentResolver mResolver = VirtualCore.get().getContext().getContentResolver();
  
  private void trimDownloadRequests() {
    Uri uri = Uri.parse("content://downloads/my_downloads");
    Cursor cursor = this.mResolver.query(uri, new String[] { "_id" }, null, null, null);
    if (cursor != null) {
      while (cursor.moveToNext()) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("download id: ");
        stringBuilder.append(cursor.getLong(0));
        VLog.e("DownloadManager", stringBuilder.toString());
      } 
      cursor.close();
    } 
  }
}


/* Location:              F:\何章易\项目文件夹\项目24\va\classes_merge.jar!\com\lody\virtual\server\downloads\VDownloadService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */