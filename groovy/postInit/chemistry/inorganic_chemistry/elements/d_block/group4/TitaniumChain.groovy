import globals.Globals
import static globals.CarbonGlobals.*

MIXER = recipemap('mixer')
FF = recipemap('froth_flotation')
CLARIFIER = recipemap('clarifier')
CENTRIFUGE = recipemap('centrifuge')
ELECTROMAGNETIC_SEPARATOR = recipemap('electromagnetic_separator')
BR = recipemap('batch_reactor')
SIFTER = recipemap('sifter')
FLUIDIZED_BED_REACTOR = recipemap('fluidized_bed_reactor')
VACUUM_FREEZER = recipemap('vacuum_freezer')
DISTILLATION_TOWER = recipemap('distillation_tower')
VACUUM_CHAMBER = recipemap('vacuum_chamber')
MACERATOR = recipemap('macerator')
FIXED_BED_REACTOR = recipemap('fixed_bed_reactor')
EBF = recipemap('electric_blast_furnace')

// Hot Titanium Ingot * 1
mods.gregtech.electric_blast_furnace.removeByInput(480, [metaitem('dustMagnesium') * 2], [fluid('titanium_tetrachloride') * 1000])

// Benefication

MIXER.recipeBuilder()
        .inputs(ore('dustImpureIlmenite') * 8)
        .fluidInputs(fluid('distilled_water') * 2000)
        .fluidOutputs(fluid('impure_ilmenite_slurry') * 2000)
        .EUt(Globals.voltAmps[3])
        .duration(80)
        .buildAndRegister()

FF.recipeBuilder()
        .fluidInputs(fluid('impure_ilmenite_slurry') * 2000)
        .notConsumable(fluid('diluted_sulfuric_acid') * 1000)
        .notConsumable(fluid('oleic_acid') * 144)
        .notConsumable(ore('dustSodiumFluoride') * 2)
        .fluidOutputs(fluid('ilmenite_slurry') * 1000)
        .fluidOutputs(fluid('ultramafic_tailing_slurry') * 1000)
        .EUt(Globals.voltAmps[3])
        .duration(80)
        .buildAndRegister()

CLARIFIER.recipeBuilder()
        .fluidInputs(fluid('ilmenite_slurry') * 1000)
        .outputs(metaitem('dustConcentrateIlmenite') * 16)
        .fluidOutputs(fluid('wastewater') * 1000)
        .duration(20)
        .EUt(Globals.voltAmps[1])
        .buildAndRegister()

CENTRIFUGE.recipeBuilder()
        .fluidInputs(fluid('ultramafic_tailing_slurry') * 1000)
        .outputs(metaitem('dustUltramaficTailings') * 2)
        .fluidOutputs(fluid('wastewater') * 1000)
        .duration(20)
        .EUt(Globals.voltAmps[1])
        .buildAndRegister()

ELECTROMAGNETIC_SEPARATOR.recipeBuilder()
        .inputs(ore('dustConcentrateIlmenite'))
        .outputs(metaitem('dustIlmenite'))
        .chancedOutput(metaitem('dustMagnetite'), 1000, 250)
        .chancedOutput(metaitem('dustConcentrateRutile'), 1000, 250)
        .duration(20)
        .EUt(Globals.voltAmps[1])
        .buildAndRegister()

ELECTROMAGNETIC_SEPARATOR.recipeBuilder()
        .inputs(ore('dustConcentrateRutile'))
        .outputs(metaitem('dustRutile'))
        .chancedOutput(metaitem('dustZircon'), 1000, 250)
        .duration(20)
        .EUt(Globals.voltAmps[1])
        .buildAndRegister()

ELECTROMAGNETIC_SEPARATOR.recipeBuilder()
        .inputs(ore('dustImpurePerovskite'))
        .outputs(metaitem('dustPerovskite'))
        .chancedOutput(metaitem('dustConcentrateRutile'), 1000, 250)
        .duration(20)
        .EUt(Globals.voltAmps[1])
        .buildAndRegister()

// Sulfate Process

BR.recipeBuilder()
        .fluidInputs(fluid('oleum') * 1000)
        .inputs(ore('dustIlmenite'))
        .fluidOutputs(fluid('titanyl_sulfate_solution') * 1000)
        .outputs(metaitem('dustUltramaficTailings'))
        .duration(200)
        .EUt(Globals.voltAmps[1])
        .buildAndRegister()

MIXER.recipeBuilder()
        .fluidInputs(fluid('gtfo_heated_water') * 1000)
        .fluidInputs(fluid('titanyl_sulfate_solution') * 1000)
        .fluidOutputs(fluid('rutile_slurry') * 2000)
        .duration(160)
        .EUt(Globals.voltAmps[1])
        .buildAndRegister()

SIFTER.recipeBuilder()
        .fluidInputs(fluid('rutile_slurry') * 2000)
        .notConsumable(metaitem('item_filter'))
        .fluidOutputs(fluid('acidic_wastewater') * 2000)
        .outputs(metaitem('dustRutile') * 3)
        .duration(200)
        .EUt(Globals.voltAmps[0])
        .buildAndRegister()

BR.recipeBuilder()
        .fluidInputs(fluid('acidic_wastewater') * 1000)
        .inputs(ore('dustSodaAsh'))
        .fluidOutputs(fluid('wastewater') * 1000)
        .fluidOutputs(fluid('carbon_dioxide') * 1000)
        .duration(200)
        .EUt(Globals.voltAmps[1])
        .buildAndRegister()

// Chloride Ilmenite Process

for (highPurityCombustible in CarbonGlobals.highPurityCombustibles()) {
        FLUIDIZED_BED_REACTOR.recipeBuilder()
        .fluidInputs(fluid('chlorine') * 3000)
        .inputs(ore('dustIlmenite'))
        .inputs(ore(highPurityCombustible.name) * highPurityCombustible.equivalent(1))
        .chancedOutput(metaitem(highPurityCombustible.byproduct), 1000, 0)
        .fluidOutputs(fluid('gaseous_iron_iii_chloride') * 1000)
        .fluidOutputs(fluid('carbon_monoxide') * 1000)
        .outputs(metaitem('dustRutile') * 3)
        .duration(100 * highPurityCombustible.duration)
        .EUt(Globals.voltAmps[1])
        .buildAndRegister()

        FLUIDIZED_BED_REACTOR.recipeBuilder()
        .fluidInputs(fluid('chlorine') * 2000)
        .inputs(ore('dustPerovskite'))
        .inputs(ore(highPurityCombustible.name) * highPurityCombustible.equivalent(1))
        .chancedOutput(metaitem(highPurityCombustible.byproduct), 1000, 0)
        .fluidOutputs(fluid('calcium_chloride') * 432)
        .fluidOutputs(fluid('carbon_monoxide') * 1000)
        .outputs(metaitem('dustRutile') * 3)
        .duration(100 * highPurityCombustible.duration)
        .EUt(Globals.voltAmps[1])
        .buildAndRegister()

        FLUIDIZED_BED_REACTOR.recipeBuilder()
        .inputs(ore('dustRutile') * 3)
        .inputs(ore(highPurityCombustible.name) * highPurityCombustible.equivalent(2))
        .fluidInputs(fluid('chlorine') * 5000)
		/*calculation:	 1000 TiCl4	* 4
						+50 AlCl3	* 3
						+1/4 FeCl3	* 20%	* 3000
						+1/4 VOCl2	* 60%	* 2000
						+50 SnCl4	*4
						+50	SiCl4	*4
		*/
        .chancedOutput(metaitem(highPurityCombustible.byproduct), 1000, 0)
        .fluidOutputs(fluid('gaseous_titanium_tetrachloride') * 1000)
        .fluidOutputs(fluid('carbon_monoxide') * 2000)
        .duration(50 * highPurityCombustible.duration)
        .EUt(Globals.voltAmps[2])
        .buildAndRegister()
}

VACUUM_FREEZER.recipeBuilder()
        .fluidInputs(fluid('gaseous_iron_iii_chloride') * 1000)
        .outputs(metaitem('dustIronIiiChloride') * 4)
        .duration(200)
        .EUt(Globals.voltAmps[1] * 2)
        .buildAndRegister()

VACUUM_FREEZER.recipeBuilder()
        .fluidInputs(fluid('gaseous_aluminium_chloride') * 1000)
        .outputs(metaitem('dustAluminiumChloride') * 4)
        .duration(200)
        .EUt(Globals.voltAmps[1] * 2)
        .buildAndRegister()

VACUUM_FREEZER.recipeBuilder()
        .fluidInputs(fluid('gaseous_titanium_tetrachloride') * 1000)
        .fluidOutputs(fluid('impure_titanium_tetrachloride') * 1000)
        .duration(200)
        .EUt(Globals.voltAmps[1] * 2)
        .buildAndRegister()

DISTILLATION_TOWER.recipeBuilder()
        .fluidInputs(fluid('impure_titanium_tetrachloride') * 1000)
        .fluidOutputs(fluid('distilled_titanium_tetrachloride') * 1000)
        .fluidOutputs(fluid('tin_tetrachloride') * 50)
        .fluidOutputs(fluid('silicon_tetrachloride') * 50)
        .duration(400)
        .EUt(Globals.voltAmps[2]  * 2)
        .buildAndRegister()

FIXED_BED_REACTOR.recipeBuilder()
        .fluidInputs(fluid('distilled_titanium_tetrachloride') * 1000)
        .fluidInputs(fluid('hydrogen_sulfide') * 25)
        .notConsumable(ore('catalystBedCopper'))
        .fluidOutputs(fluid('vanadium_free_titanium_tetrachloride') * 1000)
        .chancedOutput(metaitem('dustVanadiumOxydichloride'), 6000, 0)
        .duration(200)
        .EUt(Globals.voltAmps[2])
        .buildAndRegister()

DISTILLATION_TOWER.recipeBuilder()
        .fluidInputs(fluid('vanadium_free_titanium_tetrachloride') * 1000)
        .fluidOutputs(fluid('gaseous_aluminium_chloride') * 50)
        .fluidOutputs(fluid('titanium_tetrachloride') * 1000)
        .chancedOutput(metaitem('dustIronIiiChloride'), 2000, 0)
        .duration(300)
        .EUt(Globals.voltAmps[2] * 2)
        .buildAndRegister()

// Kroll Process

for (inertGas in Globals.inertGases) {
        EBF.recipeBuilder()
                .circuitMeta(2)
                .fluidInputs(fluid('titanium_tetrachloride') * 900)
                .notConsumable(fluid(inertGas.name) * inertGas.amount_required)
                .inputs(ore('dustMagnesium') * 2)
                .outputs(metaitem('sponge.titanium.crude'))
                .fluidOutputs(fluid('magnesium_chloride') * 756)
                .chancedOutput(metaitem('dustMagnesium'), 2500, 0)
                .blastFurnaceTemp(2150)
                .duration(100 * inertGas.duration)
                .EUt(Globals.voltAmps[3] * 2)
                .buildAndRegister()
}

VACUUM_CHAMBER.recipeBuilder()
        .inputs(metaitem('sponge.titanium.crude'))
        .outputs(metaitem('sponge.titanium'))
        .fluidOutputs(fluid('titanium_tetrachloride') * 25)
        .duration(100)
        .EUt(Globals.voltAmps[1])
        .buildAndRegister()

MACERATOR.recipeBuilder()
        .inputs(metaitem('sponge.titanium'))
        .chancedOutput(metaitem('dustTitanium'), 8500, 0)
        .duration(100)
        .EUt(Globals.voltAmps[1])
        .buildAndRegister()

// Byproducts

BR.recipeBuilder()
        .inputs(ore('dustVanadiumOxydichloride') * 4)
        .fluidInputs(fluid('chlorine') * 1000)
        .fluidOutputs(fluid('vanadium_oxytrichloride') * 1000)
        .duration(200)
        .EUt(Globals.voltAmps[2])
        .buildAndRegister()

BR.recipeBuilder()
        .fluidInputs(fluid('vanadium_oxytrichloride') * 2000)
        .fluidInputs(fluid('water') * 9000)
        .outputs(metaitem('dustVanadiumPentoxide') * 7)
        .fluidOutputs(fluid('hydrochloric_acid') * 6000)
        .duration(160)
        .EUt(Globals.voltAmps[1])
        .buildAndRegister()

BR.recipeBuilder()
        .fluidInputs(fluid('tin_tetrachloride')* 1000)
        .fluidInputs(fluid('water') * 6000)
        .outputs(metaitem('dustTinIvOxide') * 3)
        .fluidOutputs(fluid('hydrochloric_acid') * 4000)
        .duration(160)
        .EUt(Globals.voltAmps[1])
        .buildAndRegister()

//VAN ARKEL-DE BOER

BR.recipeBuilder()
        .inputs(ore('dustTitanium'))
        .inputs(ore('dustAnyPurityIodine') * 4)
        .outputs(metaitem('dustTitaniumIodide') * 5)
        .duration(240)
        .EUt(Globals.voltAmps[3])
        .buildAndRegister()

CVD.recipeBuilder()
        .inputs(ore('dustTitaniumIodide') * 5)
        .notConsumable(metaitem('stickTungsten'))
        .outputs(metaitem('dustHighPurityTitanium'))
        .fluidOutputs(fluid('iodine') * 576)
        .duration(120)
        .EUt(Globals.voltAmps[4])
        .buildAndRegister()
