package cn.shadow.vacation_diary.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.google.gson.Gson;

import cn.shadow.vacation_diary.VacationDiary;
import cn.shadow.vacation_diary.dimension.VocationCityWorldSettings;

public class JsonConfiguration {
	
	private static String path = "config" + File.separator + VacationDiary.MOD_ID;
	private VocationCityWorldSettings settings;
	private final String filename;
	
	public VocationCityWorldSettings getSettings() {
		return this.settings;
	}
	
	public static File getDataFolder() {
		return new File(path);
	}
	
	public JsonConfiguration(String filename) {
		this.filename = filename;
	}
	
	public void saveConfig() {
		if(filename == null) return;
		Gson gson = new Gson();
		String json = gson.toJson(this.settings);
        File file = new File(new File(path), filename);
        try(OutputStreamWriter oStreamWriter = new OutputStreamWriter(new FileOutputStream(file), "utf-8")) {
            oStreamWriter.append(json);
            oStreamWriter.close();
        } catch (IOException ex) {
        	ex.printStackTrace();
        }
	}
	
	public void setSettings(VocationCityWorldSettings settings) {
		this.settings = settings;
	}
	
	public void loadConfig() {
		if(filename == null) return;
		File file = new File(new File(path), filename);
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"))) {
            StringBuilder sb = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                sb.append(line);
                line = reader.readLine();
            }
            this.settings = (new Gson()).fromJson(sb.toString(), VocationCityWorldSettings.class);
        } catch (Exception ex) {
        	this.settings = new VocationCityWorldSettings();
        	ex.printStackTrace();
        }
	}
}
