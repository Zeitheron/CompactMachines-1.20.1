package dev.compactmods.machines.advancement.trigger;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class BasicPlayerAdvTrigger
		extends BaseAdvancementTrigger<BasicPlayerAdvTrigger.Instance>
{
	private final ResourceLocation advancementId;
	
	public BasicPlayerAdvTrigger(ResourceLocation advancementId)
	{
		this.advancementId = advancementId;
	}
	
	@Override
	public ResourceLocation getId()
	{
		return advancementId;
	}
	
	@Override
	public Instance createInstance(JsonObject json, DeserializationContext conditions)
	{
		ContextAwarePredicate contextawarepredicate = ContextAwarePredicate.fromElement("player", conditions, json.get("player"), LootContextParamSets.ADVANCEMENT_LOCATION);
		return new Instance(this.advancementId, contextawarepredicate);
	}
	
	public static class Instance
			extends AbstractCriterionTriggerInstance
	{
		public Instance(ResourceLocation advId, ContextAwarePredicate player)
		{
			super(advId, player);
		}
		
		public static Instance create(ResourceLocation advancement)
		{
			return new Instance(advancement, ContextAwarePredicate.ANY);
		}
	}
}