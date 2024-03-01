package verifycode.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import verifycode.service.VerifyCodeService;
import verifycode.util.CookieUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author fdse
 */
@Service
public class VerifyCodeServiceImpl implements VerifyCodeService {

    public static final int CAPTCHA_EXPIRED = 1000;
    private static final Logger LOGGER = LoggerFactory.getLogger(VerifyCodeServiceImpl.class);

    String ysbCaptcha = "YsbCaptcha";

    private static char mapTable[] = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    @Override
    public Map<String, Object> getImageCode(int width, int height, OutputStream os, HttpServletRequest request, HttpServletResponse response, HttpHeaders headers) {
        String cookieId;
        Cookie cookie = CookieUtil.getCookieByName(request, ysbCaptcha);
        
        if (cookie == null) {
            cookieId = UUID.randomUUID().toString().replace("-", "").toUpperCase();
            CookieUtil.addCookie(response, ysbCaptcha, cookieId, CAPTCHA_EXPIRED);
        } else {
            if (cookie.getValue() != null) {
                cookieId = UUID.randomUUID().toString().replace("-", "").toUpperCase();
                CookieUtil.addCookie(response, ysbCaptcha, cookieId, CAPTCHA_EXPIRED);
            } else {
                cookieId = cookie.getValue();
            }
        }
 
        if (width <= 0) {
            width = 60;
        }
        if (height <= 0) {
            height = 20;
        }

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        Graphics g = image.getGraphics();

        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 18));

        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 168; i++) {
            int x = ThreadLocalRandom.current().nextInt(width + 1);
            int y = ThreadLocalRandom.current().nextInt(height + 1);
            int xl = ThreadLocalRandom.current().nextInt(13);
            int yl = ThreadLocalRandom.current().nextInt(13);
            
            g.drawLine(x, y, x + xl, y + yl);
        }

        String strEnsure = code(cookieId);
        VerifyCodeServiceImpl.LOGGER.info(" {}  ___ st", strEnsure);
        
        for (int i = 0; i < 4; ++i) {
            g.setColor(new Color(
                20 + ThreadLocalRandom.current().nextInt(0, 111),
                20 + ThreadLocalRandom.current().nextInt(0, 111),
                20 + ThreadLocalRandom.current().nextInt(0, 111))
            );

            String str = strEnsure.substring(i, i + 1);
            g.drawString(str, 13 * i + 6, 16);
        }

        g.dispose();

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("image", image);
        returnMap.put("strEnsure", strEnsure);

        return returnMap;
    }

    @Override
    public boolean verifyCode(HttpServletRequest request, HttpServletResponse response, String receivedCode, HttpHeaders headers) {
        boolean result = false;
        String cookieId;
        Cookie cookie = CookieUtil.getCookieByName(request, ysbCaptcha);
        
        if (cookie == null) {
            cookieId = UUID.randomUUID().toString().replace("-", "").toUpperCase();
            CookieUtil.addCookie(response, ysbCaptcha, cookieId, CAPTCHA_EXPIRED);
        } else {
            cookieId = cookie.getValue();
        }

        String code = code(cookieId);
        LOGGER.info("GET Code By cookieId " + cookieId + "   is :" + code);
        return code.equalsIgnoreCase(receivedCode);
    }

    @Cacheable("tokens")
    public String code(String cookieId) {
        String code = "";
        for (int i = 0; i < 4; ++i) {
            code += mapTable[ThreadLocalRandom.current().nextInt(0, mapTable.length)];
        }
        return code;
    }

    static Color getRandColor(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }

        int r = fc + ThreadLocalRandom.current().nextInt(0, bc - fc + 1);
        int g = fc + ThreadLocalRandom.current().nextInt(0, bc - fc + 1);
        int b = fc + ThreadLocalRandom.current().nextInt(0, bc - fc + 1);
        return new Color(r, g, b);
    }
}
