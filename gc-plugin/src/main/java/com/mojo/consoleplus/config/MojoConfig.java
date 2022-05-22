package com.mojo.consoleplus.config;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import emu.grasscutter.Grasscutter;

public class MojoConfig {

    public boolean UseCDN = false;
    public String CDNLink = "https://gc-mojoconsole.github.io/";
    public String interfacePath = "/mojoplus/console.html";
    public String responseMessage = "[MOJO] 请你打开你的邮件，查看你的链接！";
    public String responseMessageThird = "[MojoConsole] You are trying to obtain link for third-party user, please ask him/her send \"/mojo {{OTP}}\" to server in-game";
    public String responseMessageError = "[MojoConsole] Invalid argument.";
    public String responseMessageSuccess = "[MojoConsole] Success!";
    
    static public class MailTemplate {
        public String title = "看这里！！！！！";
        public String author = "QWQ";
        public String content = "这是你的链接: {{ LINK }}\n" +
        "拿起你的小手指点击上面这个 <b>链接</b> 这个MOJO端可以极大缩短你运行命令的时间！！！";
        public int expireHour = 3;
    };

    public MailTemplate mail = new MailTemplate();

    static String getConfigPath(){
        try{
            String result = new File(MojoConfig.class.getProtectionDomain().getCodeSource().getLocation()
            .toURI()).getParent() + "/mojoconfig.json";
            return result;
        } catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public static MojoConfig loadConfig(){
        String configPath = getConfigPath();
        File configFile = new File(configPath);
        Gson gson = new Gson();

        try{
            String s = Files.readString(configFile.toPath(), StandardCharsets.UTF_8);
            MojoConfig config = gson.fromJson(s, MojoConfig.class);     
            config.saveConfig();
            return config;
        } catch (Exception e) {
            MojoConfig config = new MojoConfig();
            config.saveConfig();
            return config;
        }
    }

    public void saveConfig(){
        String configPath = getConfigPath();
        File configFile = new File(configPath);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try{
            Files.writeString(configFile.toPath(), gson.toJson(this, MojoConfig.class));            
        } catch (Exception e) {
            e.printStackTrace();
            Grasscutter.getLogger().error("[Mojoconsole] Config save failed!");
        }
    }
}
