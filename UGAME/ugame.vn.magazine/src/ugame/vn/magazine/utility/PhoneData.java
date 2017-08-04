package ugame.vn.magazine.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneData {
	private static final String FILE_PATH = "phoneData.dat";
	private Context context;

	public PhoneData(Context context) {
		this.context = context;
	}

	public String getIMIENumber() {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId();
	}

	public boolean checkFileExitance() {
		File file = (File) context.getFileStreamPath(FILE_PATH);
		if (file.exists()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Read file in Android, using openFileInput in Android to read
	 * FileInputStream
	 */
	public String readFileData() {
		try {
			FileInputStream in = context.openFileInput(FILE_PATH);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			String data = "";
			StringBuilder builder = new StringBuilder();
			while ((data = reader.readLine()) != null) {
				builder.append(data);
				builder.append("\n");
			}
			in.close();
			// String fileData = builder.toString();
			return builder.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> getCurrentAppInstalling() {
		List<String> currentInstallApps = new ArrayList<String>();
		final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ApplicationInfo> infos = context.getPackageManager()
				.getInstalledApplications(PackageManager.GET_META_DATA);
		if (infos == null || infos.isEmpty()) {
			return null;
		} else {
			for (ApplicationInfo applicationInfo : infos) {
				currentInstallApps.add(applicationInfo.packageName);
			}
		}
		return currentInstallApps;
	}

	/**
	 * Write file data in Android, used openFileOutput to write openFileOutput,
	 * make FileOutputStream
	 */
	public void writeFileData(String data) {
		try {
			FileOutputStream out = context.openFileOutput(FILE_PATH, 0);
			OutputStreamWriter writer = new OutputStreamWriter(out);
			writer.write(data);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
