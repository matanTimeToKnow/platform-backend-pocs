package samlSsoPoc;

import com.onelogin.saml2.Auth;
import com.onelogin.saml2.settings.Saml2Settings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class SsoSamlController {

    @RequestMapping(method = POST, value = "/saml/sso")
    public String post_saml(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        try{
            Auth auth = new Auth(httpServletRequest, httpServletResponse);
            auth.processResponse();
            if (!auth.isAuthenticated()) {
                System.out.println("Not authenticated");
            }
            List<String> errors = auth.getErrors();
            if (!errors.isEmpty()) {
                System.out.println("Authentication Errors: " + StringUtils.join(errors, ", "));
            }
            else {
                Map<String, List<String>> attributes = auth.getAttributes();
                List<String> redirectUrlList = attributes.get("redirectUrl");
                if (CollectionUtils.isEmpty(redirectUrlList)) {
                    System.out.println("Error: missing redirect url as an attribute");
                }else{
                    String redirectUrl = redirectUrlList.get(0);
                    String userId = "";
                    String SiteId = "";
                    String access_token = "jwt_dummy_token_value";
                    redirectUrl += "&access_token=" + access_token;
                    httpServletResponse.sendRedirect(redirectUrl);
                }
            }

        }catch (Exception e){
            System.out.println("Exception in saml SSO post request: " + e);
        }
        String answer = "my response";
        return answer;
    }

    @RequestMapping(method = GET, value = "/saml/metadata")
    public String saml_get_metadata(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String result = "";
        try{
            Auth auth = new Auth();
            Saml2Settings settings = auth.getSettings();
            String metadata = settings.getSPMetadata();
            List<String> errors = Saml2Settings.validateMetadata(metadata);
            if (!errors.isEmpty()) {
                System.out.println("errors in metadata: " + errors);

            } else {
                result = metadata;
            }
            httpServletResponse.setContentType("text/plain; charset=UTF-8");
            return result;
        }
        catch(Exception e){
            System.out.println("error");
            return result;
        }
    }
}