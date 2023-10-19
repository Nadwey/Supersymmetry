import static globals.Globals.*

POLYMERIZATION = recipemap('polymerization_tank')
BR = recipemap('batch_reactor')

POLYMERIZATION.recipeBuilder()
    .fluidInputs(fluid('ethylene_glycol') * 1000)
    .fluidInputs(fluid('terephthalic_acid') * 2592)
    .outputs(metaitem('dustPolyethyleneTerephthalate') * 22)
    .fluidOutputs(fluid('water') * 2000)
    .EUt(Globals.voltAmps[3])
    .duration(80)
    .buildAndRegister()

BR.recipeBuilder()
    .inputs(ore('dustCobaltOxide') * 2)
    .fluidInputs(fluid('acetic_acid') * 2000)
    .fluidInputs(fluid('distilled_water') * 3000)
    .outputs(metaitem('dustCobaltIiAcetate') * 18)
    .EUt(Globals.voltAmps[2])
    .duration(80)
    .buildAndRegister()

POLYMERIZATION.recipeBuilder()
    .notConsumable(ore('dustCobaltIiAcetate'))
    .fluidInputs(fluid('ethylene_glycol') * 1000)
    .fluidInputs(fluid('dimethyl_terephthalate') * 3456)
    .fluidOutputs(fluid('methanol') * 2000)
    .EUt(Globals.voltAmps[3])
    .duration(80)
    .buildAndRegister()