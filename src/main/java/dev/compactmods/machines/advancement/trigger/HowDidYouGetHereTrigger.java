package dev.compactmods.machines.advancement.trigger;

import com.google.gson.JsonObject;
import dev.compactmods.machines.api.core.Advancements;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class HowDidYouGetHereTrigger
		extends BaseAdvancementTrigger<HowDidYouGetHereTrigger.Instance>
{
	@Override
	public ResourceLocation getId()
	{
		return Advancements.HOW_DID_YOU_GET_HERE;
	}
	
	@Override
	public Instance createInstance(JsonObject json, DeserializationContext conditions)
	{
		ContextAwarePredicate contextawarepredicate = ContextAwarePredicate.fromElement("player", conditions, json.get("player"), LootContextParamSets.ADVANCEMENT_LOCATION);
		return new Instance(contextawarepredicate);
	}
	
	public static class Instance
			extends AbstractCriterionTriggerInstance
	{
		public Instance(ContextAwarePredicate player)
		{
			super(Advancements.HOW_DID_YOU_GET_HERE, player);
		}
		
		public static Instance create()
		{
			return new Instance(ContextAwarePredicate.ANY);
		}
	}
}