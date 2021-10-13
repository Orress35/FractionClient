package xyz.fraction.ui;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

import java.net.Proxy;

public class Alt {
    private final String user, pass;
    private String name;

    public Alt(String s) {
        if (s.contains("::")) {
            name = s.split("::")[1];
            s = s.split("::")[0];
        }

        if (s.contains(":")) {
            String[] args = s.split(":");
            user = args[0];
            pass = args[1];
        } else {
            name = user = s;
            pass = "";
        }
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return user + (pass.length() > 0 ? ":" + pass : "") + (name.length() > 0 ? "::" + name : 0);
    }

    public Session login() {
        Minecraft mc = Minecraft.getMinecraft();
        if (pass.length() > 0) {
            Session session = createSession(user, pass);
            if (session == null) {
                return null;
            } else {
                name = session.getUsername();
                return mc.session = session;
            }
        } else {
            return mc.session = new Session(user, "", "", "mojang");
        }
    }

    private Session createSession(String username, String password) {
        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) service.createUserAuthentication(Agent.MINECRAFT);
        auth.setUsername(username);
        auth.setPassword(password);
        try {
            auth.logIn();
            return new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");
        } catch (AuthenticationException localAuthenticationException) {
            localAuthenticationException.printStackTrace();
            return null;
        }
    }
}
