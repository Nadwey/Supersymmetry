import globals.Globals
import static globals.SinteringGlobals.*

FLOTATION = recipemap('froth_flotation')
ROASTER = recipemap('roaster')
BR = recipemap('batch_reactor')
BCR = recipemap('bubble_column_reactor')
DISTILLERY = recipemap('distillery')
MIXER = recipemap('mixer')
CLARIFIER = recipemap('clarifier')
CENTRIFUGE = recipemap('centrifuge')
REACTION_FURNACE = recipemap('reaction_furnace')
GRAVITY_SEPARATOR = recipemap('gravity_separator')
ROTARY_KILN = recipemap('rotary_kiln')

GRAVITY_SEPARATOR.recipeBuilder()
    .inputs(ore('dustCelestine'))
    .outputs(metaitem('dustSiftedCelestine'))
    .chancedOutput(metaitem('dustLimestone'), 2500, 0)
    .chancedOutput(metaitem('dustDolomite'), 2500, 0)
    .EUt(Globals.voltAmps[1])
    .duration(40)
    .buildAndRegister()

MIXER.recipeBuilder()
    .inputs(ore('dustSiftedCelestine') * 8)
    .fluidInputs(fluid('distilled_water') * 2000)
    .fluidOutputs(fluid('impure_celestine_slurry') * 2000)
    .EUt(Globals.voltAmps[3])
    .duration(80)
    .buildAndRegister()

FLOTATION.recipeBuilder()
    .fluidInputs(fluid('impure_celestine_slurry') * 2000)
    .notConsumable(fluid('methyl_isobutyl_carbinol') * 100)
    .notConsumable(fluid('alkaline_sodium_oleate_solution') * 100)
    .fluidOutputs(fluid('celestine_slurry') * 1000)
    .fluidOutputs(fluid('limestone_tailing_slurry') * 1000)
    .EUt(Globals.voltAmps[3])
    .duration(80)
    .buildAndRegister()

CLARIFIER.recipeBuilder()
    .fluidInputs(fluid('celestine_slurry') * 1000)
    .outputs(metaitem('dustFlotatedCelestine') * 16)
    .fluidOutputs(fluid('wastewater') * 1000)
    .EUt(Globals.voltAmps[1])
    .duration(20)
    .buildAndRegister()

for (fuel in rotary_kiln_fuels) {
    for (comburent in rotary_kiln_comburents) {
        ROTARY_KILN.recipeBuilder()
            .inputs(ore('dustFlotatedCelestine'))
            .inputs(ore('dustAnyPurityCarbon') * 2)
            .fluidInputs(fluid(fuel.name) * fuel.amountRequired)
            .fluidInputs(fluid(comburent.name) * comburent.amountRequired)
            .outputs(metaitem('dustImpureStrontiumSulfide') * 2)
            .fluidOutputs(fluid('carbon_dioxide') * 2025)
            .EUt(Globals.voltAmps[1])
            .duration(fuel.duration + comburent.duration)
            .buildAndRegister()
    }
}

MIXER.recipeBuilder()
    .inputs(ore('dustImpureStrontiumSulfide') * 4)
    .fluidInputs(fluid('gtfo_heated_water') * 2000)
    .fluidOutputs(fluid('impure_strontium_sulfide_slurry') * 2000)
    .EUt(Globals.voltAmps[1])
    .duration(100)
    .buildAndRegister()

CLARIFIER.recipeBuilder()
    .fluidInputs(fluid('impure_strontium_sulfide_slurry') * 1000)
    .outputs(metaitem('dustSiliconDioxide'))
    .fluidOutputs(fluid('strontium_sulfide_solution') * 1000)
    .EUt(Globals.voltAmps[1])
    .duration(20)
    .buildAndRegister()

BR.recipeBuilder()
    .inputs(ore('dustSodaAsh') * 6)
    .fluidInputs(fluid('strontium_sulfide_solution') * 1000)
    .fluidInputs(fluid('distilled_water') * 4000)
    .outputs(metaitem('dustStrontiumCarbonate') * 5)
    .fluidOutputs(fluid('hydrogen_sulfide') * 1000)
    .fluidOutputs(fluid('sodium_hydroxide_solution') * 2000)
    .EUt(Globals.voltAmps[1])
    .duration(100)
    .buildAndRegister()

ROASTER.recipeBuilder()
    .inputs(ore('dustStrontiumCarbonate') * 5)
    .outputs(metaitem('dustStrontiumOxide') * 2)
    .fluidOutputs(fluid('carbon_dioxide') * 1000)
    .EUt(Globals.voltAmps[3])
    .duration(20)
    .buildAndRegister()

ROASTER.recipeBuilder()
    .inputs(ore('dustStrontianite') * 5)
    .outputs(metaitem('dustStrontiumOxide') * 2)
    .fluidOutputs(fluid('carbon_dioxide') * 1000)
    .EUt(Globals.voltAmps[3])
    .duration(20)
    .buildAndRegister()
    
CENTRIFUGE.recipeBuilder()
    .inputs(ore('dustStrontianite') * 5)
    .outputs(metaitem('dustStrontiumCarbonate') * 5)
    .EUt(Globals.voltAmps[1])
    .duration(80)
    .buildAndRegister()

REACTION_FURNACE.recipeBuilder()
    .inputs(ore('dustAnyPurityAluminium') * 2)
    .inputs(ore('dustStrontiumOxide') * 6)
    .outputs(metaitem('dustStrontium') * 3)
    .outputs(metaitem('dustAlumina') * 5)
    .EUt(Globals.voltAmps[2])
    .duration(100)
    .buildAndRegister()



