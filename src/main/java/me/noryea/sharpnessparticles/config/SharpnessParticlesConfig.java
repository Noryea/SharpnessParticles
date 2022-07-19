package me.noryea.sharpnessparticles.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class SharpnessParticlesConfig extends MidnightConfig {

    public enum SharpnessParticlesMode { NEVER, VANILLA, ALWAYS }
    @Entry public static SharpnessParticlesMode showSharpnessParticles = SharpnessParticlesMode.ALWAYS;

    @Entry(min = 0, max = 20, width = 2) public static int multiplier = 3;

}
