package vv0ta3fa9.plugin.utils.color.impl;


import vv0ta3fa9.plugin.KReferalSystem;
import vv0ta3fa9.plugin.utils.color.Colorizer;

public class VanillaColorizer implements Colorizer {

    private final KReferalSystem plugin;

    public VanillaColorizer(KReferalSystem plugin) {
        this.plugin = plugin;
    }

    @Override
    public String colorize(String message) {
        if (message == null || message.isEmpty()) {
            return message;
        }
        return plugin.getUtils().translateAlternateColorCodes('&', message);
    }
}
