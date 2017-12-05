package com.lf.hz.service;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.lf.hz.config.Config;
import com.lf.hz.model.Article;
import com.lf.hz.repository.ArticleRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

@Service
public class TTSService {

    @Autowired
    private Config config;

    @Autowired
    private ArticleRepository articleRepository;

    protected Boolean soundExists(String filename) {
        File sound = new File(getSoundPathByFilename(filename));
        return sound.exists();
    }

    protected String getSoundPathByFilename(String filename) {
        return Paths.get(config.getResoucesPath(), "sound", filename + ".mp3").toString();
    }

    protected String getSoundResourcePath(String filename) {
        String relativePath = new File(getSoundPathByFilename(filename)).getAbsolutePath().substring(config.getResoucesPath().length());
        return config.getHost() + '/' + Paths.get("sound", relativePath).toString();
    }

    public String getArticleSound(Integer id) throws IOException {
        String filename = "article" + id;
        if (!soundExists(filename)) {
            Article article = articleRepository.findOneById(id);
            if (article != null) {
                String text = article.getInnerText();
                getSound(text, filename);
            }
        }
        return getSoundResourcePath(filename);

    }

    public String getTextSound(String text) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(text.getBytes());
        String filename = "text" + new BigInteger(1, md.digest()).toString(16) + ".mp3";
        if (!soundExists(filename)) {
            getSound(text, filename);
        }
        return getSoundResourcePath(filename);
    }

    public Boolean getSound(String text, String filename) throws IOException {
        AipSpeech client = new AipSpeech(config.getTtsAppid(), config.getTtsApikey(), config.getTtsSecretkey());

        String toSaveFilePath = getSoundPathByFilename(filename);

        FileOutputStream fos = new FileOutputStream(toSaveFilePath, true);
        while (text.length() > 0) {
            String tmpText = text.substring(0, text.length() < 1000 ? text.length() : 1000);
            text = text.length() < 1000 ? "" : text.substring(1000);
            TtsResponse res = client.synthesis(tmpText, "zh", 1, null);

            JSONObject result = res.getResult();
            byte[] data = res.getData();
            if (data != null) {
                fos.write(res.getData());
            } else {
                if (result != null) {
                    Logger logger = Logger.getLogger(this.getClass().toString());
                    logger.info(tmpText);
                    logger.info(result.get("err_no").toString());
                    logger.info(result.get("err_msg").toString());
                }
                break;
            }
        }
        fos.close();

        return true;
    }


}
