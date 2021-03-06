package jodd.petite.prox;

import jodd.petite.PetiteConfig;
import jodd.proxetta.ProxyAspect;
import jodd.proxetta.ProxyPointcut;
import jodd.proxetta.impl.ProxyProxetta;
import jodd.proxetta.pointcuts.MethodAnnotationPointcut;

public class PetiteHelper {

    public static PetiteConfig createPetiteConfig() {
        PetiteConfig petiteConfig = new PetiteConfig();
        petiteConfig.setDetectDuplicatedBeanNames(true);
        petiteConfig.setWireScopedProxy(true);
        petiteConfig.setDetectMixedScopes(true);

        return petiteConfig;
    }

    public static ProxyProxetta createProxyProxetta() {

        ProxyPointcut pointcut_logged = new MethodAnnotationPointcut(Logged.class);
        ProxyAspect aspect_logged = new ProxyAspect(LogProxyAdvice.class, pointcut_logged);

        ProxyProxetta proxetta = ProxyProxetta.withAspects(aspect_logged);
        //proxetta.setDebugFolder(SystemUtil.userHome() + "\\inka\\proxetta");

        return proxetta;
    }

}
