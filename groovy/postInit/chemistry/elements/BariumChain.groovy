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
ROTARY_KILN = recipemap('rotary_kiln')
CRYSTALLIZER = recipemap('crystallizer')

//OPTIONAL FLOTATION

MIXER.recipeBuilder()
    .inputs(ore('dustImpureBarite') * 8)
    .fluidInputs(fluid('distilled_water') * 2000)
    .fluidOutputs(fluid('impure_barite_slurry') * 2000)
    .EUt(Globals.voltAmps[3])
    .duration(80)
    .buildAndRegister()

FLOTATION.recipeBuilder()
    .fluidInputs(fluid('impure_barite_slurry') * 2000)
    .notConsumable(fluid('methyl_isobutyl_carbinol') * 100)
    .notConsumable(fluid('lauric_acid') * 100)
    .fluidOutputs(fluid('barite_slurry') * 1000)
    .fluidOutputs(fluid('granite_tailing_slurry') * 1000)
    .EUt(Globals.voltAmps[3])
    .duration(80)
    .buildAndRegister()

CLARIFIER.recipeBuilder()
    .fluidInputs(fluid('barite_slurry') * 1000)
    .outputs(metaitem('dustBarite') * 16)
    .fluidOutputs(fluid('wastewater') * 1000)
    .EUt(Globals.voltAmps[1])
    .duration(20)
    .buildAndRegister()

CENTRIFUGE.recipeBuilder()
    .fluidInputs(fluid('granite_tailing_slurry') * 1000)
    .outputs(metaitem('dustGraniteTailings') * 2)
    .fluidOutputs(fluid('wastewater') * 1000)
    .EUt(Globals.voltAmps[1])
    .duration(20)
    .buildAndRegister()

for (fuel in rotary_kiln_fuels) {
    for (comburent in rotary_kiln_comburents) {
        ROTARY_KILN.recipeBuilder()
            .inputs(ore('dustBarite'))
            .inputs(ore('dustAnyPurityCarbon') * 2)
            .fluidInputs(fluid(fuel.name) * fuel.amountRequired)
            .fluidInputs(fluid(comburent.name) * comburent.amountRequired)
            .outputs(metaitem('dustImpureBariumSulfide') * 2)
            .fluidOutputs(fluid('carbon_dioxide') * 2025)
            .EUt(Globals.voltAmps[1])
            .duration(fuel.duration + comburent.duration)
            .buildAndRegister()
    }
}

MIXER.recipeBuilder()
    .inputs(ore('dustImpureBariumSulfide') * 4)
    .fluidInputs(fluid('gtfo_heated_water') * 1000)
    .outputs(metaitem('dustBariumResidue') * 2)
    .fluidOutputs(fluid('impure_barium_sulfide_solution') * 1000)
    .EUt(Globals.voltAmps[1])
    .duration(100)
    .buildAndRegister()

BR.recipeBuilder()
    .inputs(ore('dustBariumResidue') * 2)
    .fluidInputs(fluid('gtfo_heated_water') * 1000)
    .fluidInputs(fluid('hydrogen_sulfide') * 1000)
    .fluidOutputs(fluid('impure_barium_sulfide_solution') * 1000)
    .outputs(metaitem('dustSiliconDioxide'))
    .EUt(Globals.voltAmps[1])
    .duration(100)
    .buildAndRegister()

CRYSTALLIZER.recipeBuilder()
    .fluidInputs(fluid('impure_barium_sulfide_solution') * 1000)
    .outputs(metaitem('dustBariumSulfide') * 2)
    .fluidOutputs(fluid('wastewater') * 1000)
    .EUt(Globals.voltAmps[1])
    .duration(100)
    .buildAndRegister()

BR.recipeBuilder()
    .inputs(ore('dustBariumSulfide') * 2)
    .inputs(ore('dustSodaAsh') * 6)
    .fluidInputs(fluid('distilled_water') * 4000)
    .outputs(metaitem('dustBariumCarbonate') * 5)
    .fluidOutputs(fluid('hydrogen_sulfide') * 1000)
    .fluidOutputs(fluid('sodium_hydroxide_solution') * 2000)
    .EUt(Globals.voltAmps[1])
    .duration(100)
    .buildAndRegister()

BR.recipeBuilder()
    .inputs(ore('dustBariumSulfide') * 2)
    .fluidInputs(fluid('carbon_dioxide') * 1000)
    .fluidInputs(fluid('distilled_water') * 2000)
    .outputs(metaitem('dustBariumCarbonate') * 5)
    .fluidOutputs(fluid('hydrogen_sulfide') * 1000)
    .fluidOutputs(fluid('wastewater') * 1000)
    .EUt(Globals.voltAmps[1])
    .duration(100)
    .buildAndRegister()

BR.recipeBuilder()
    .inputs(ore('dustBariumCarbonate') * 5)
    .fluidInputs(fluid('hydrochloric_acid') * 2000)
    .fluidOutputs(fluid('barium_chloride_solution') * 1000)
    .fluidOutputs(fluid('carbon_dioxide') * 1000)
    .EUt(Globals.voltAmps[1])
    .duration(100)
    .buildAndRegister()

DISTILLERY.recipeBuilder()
    .fluidInputs(fluid('barium_chloride_solution') * 1000)
    .outputs(metaitem('dustBariumChloride') * 3)
    .fluidOutputs(fluid('water') * 2000)
    .EUt(Globals.voltAmps[1])
    .duration(100)
    .buildAndRegister()

REACTION_FURNACE.recipeBuilder()
    .inputs(ore('dustAnyPurityAluminium') * 2)
    .inputs(ore('dustBariumOxide') * 6)
    .outputs(metaitem('dustBarium') * 3)
    .outputs(metaitem('dustAlumina') * 5)
    .EUt(Globals.voltAmps[2])
    .duration(100)
    .buildAndRegister()

ROASTER.recipeBuilder()
    .inputs(ore('dustWitherite') * 5)
    .outputs(metaitem('dustBariumOxide') * 2)
    .fluidOutputs(fluid('carbon_dioxide') * 1000)
    .EUt(Globals.voltAmps[3])
    .duration(20)
    .buildAndRegister()
    
CENTRIFUGE.recipeBuilder()
    .inputs(ore('dustWitherite') * 5)
    .outputs(metaitem('dustBariumCarbonate') * 5)
    .EUt(Globals.voltAmps[1])
    .duration(80)
    .buildAndRegister()

ROASTER.recipeBuilder()
    .inputs(ore('dustBariumCarbonate') * 5)
    .outputs(metaitem('dustBariumOxide') * 2)
    .fluidOutputs(fluid('carbon_dioxide') * 1000)
    .EUt(Globals.voltAmps[3])
    .duration(20)
    .buildAndRegister()



