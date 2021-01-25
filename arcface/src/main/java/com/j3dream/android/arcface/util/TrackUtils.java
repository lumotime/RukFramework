package com.j3dream.android.arcface.util;

import androidx.annotation.Nullable;

import com.arcsoft.face.FaceInfo;

import java.util.List;

/**
 * <p>文件名称: TrackUtil </p>
 * <p>所属包名: com.lumotime.arcface.util</p>
 * <p>描述:  </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/9/13 19:13 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class TrackUtils {

	/**
	 * 保持最大的人脸
	 *
	 * @param ftFaceList 人脸列表
	 */
	public static void keepMaxFace(List<FaceInfo> ftFaceList) {
		if (ftFaceList == null || ftFaceList.size() < 1) {
			return;
		}
		FaceInfo maxFaceInfo = ftFaceList.get(0);
		for (FaceInfo faceInfo : ftFaceList) {
			if (faceInfo.getRect().width() > maxFaceInfo.getRect().width()) {
				maxFaceInfo = faceInfo;
			}
		}
		ftFaceList.clear();
		ftFaceList.add(maxFaceInfo);
	}

	/**
	 * 保持最大的人脸
	 *
	 * @param ftFaceList 人脸列表
	 */
	@Nullable
	public static FaceInfo fetchMaxFace(List<FaceInfo> ftFaceList) {
		if (ftFaceList == null || ftFaceList.size() < 1) {
			return null;
		}
		FaceInfo maxFaceInfo = ftFaceList.get(0);
		for (FaceInfo faceInfo : ftFaceList) {
			if (faceInfo.getRect().width() > maxFaceInfo.getRect().width()) {
				maxFaceInfo = faceInfo;
			}
		}
		ftFaceList.clear();
		return maxFaceInfo;
	}
}
