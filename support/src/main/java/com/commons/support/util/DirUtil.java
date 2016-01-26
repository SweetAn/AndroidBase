package com.commons.support.util;

import android.os.Environment;

import java.io.File;

public class DirUtil {

  public static final String UUID_PATH = "/.androidbase/";
  public static final String UUID_FILE_PATH = UUID_PATH + "ab_uuid";
  public static final String ROOT_PATH = "/enyes/androidbase/";
  public static final String PHOTO_PATH = ROOT_PATH + "photo/";
  public static final String CACHE_PATH = ROOT_PATH + "cache/";
  public static final String LOG_PATH = ROOT_PATH + "log/";

  public static boolean isCreateDir() {
    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
      String ROOT = Environment.getExternalStorageDirectory().getPath();

      File path = new File(ROOT + ROOT_PATH);
      if (!path.exists()) {
        path.mkdirs();
      }
      path = new File(ROOT + PHOTO_PATH);
      if (!path.exists()) {
        path.mkdirs();
      }
      path = new File(ROOT + LOG_PATH);
      if (!path.exists()) {
        path.mkdirs();
      }
      path = new File(ROOT + UUID_PATH);
      if (!path.exists()) {
        path.mkdirs();
      }
      path = new File(ROOT + CACHE_PATH);
      if (!path.exists()) {
        path.mkdirs();
      }

      return true;
    } else {
      return false;
    }
  }

  /**
   * 获取存储路径
   * @param path ROOT_PATH,PHOTO_PATH,CACHE_PATH,ARTICLE_PATH,LOG_PATH
   * @return
   */
  public static String getStoragePath(String path) {
    if (isCreateDir()) {
      return Environment.getExternalStorageDirectory().getPath() + path;
    } else {
      return Environment.getExternalStorageDirectory().getPath();
    }
  }
}
