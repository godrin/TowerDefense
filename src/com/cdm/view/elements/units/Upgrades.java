package com.cdm.view.elements.units;

import java.util.Arrays;
import java.util.List;

import com.cdm.view.elements.units.upgrades.DistanceUpgrade;
import com.cdm.view.elements.units.upgrades.PowerUpgrade;
import com.cdm.view.elements.units.upgrades.ShotUpgrade;
import com.cdm.view.elements.units.upgrades.SpeedUpgrade;

public class Upgrades {
	public static final List<Upgrade> UPGRADES = Arrays.asList(new Upgrade[] {
			new DistanceUpgrade(), new PowerUpgrade(), new SpeedUpgrade(),
			new ShotUpgrade() });
}
