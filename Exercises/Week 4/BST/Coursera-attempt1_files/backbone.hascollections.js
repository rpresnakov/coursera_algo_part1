define(["backbone","underscore"],function(Backbone,_){var HasCollectionsModel=Backbone.Model.extend({relations:{},set:function(key,value,options){var self=this,attrs;if(_.isObject(key))attrs=_(key).clone(),options=value;else attrs={},attrs[key]=value;_(self.relations).each(function(Collection,key){if(!_.has(self.attributes,key))self.attributes[key]=new Collection,self.attributes[key].on("all",self._onCollectionEvent,self),self.attributes[key].relationParent=self;if(!_.has(attrs,key))return;var collection=self.attributes[key],newItems=attrs[key];if(options&&options.merge)_(newItems).each(function(newItem){var oldItem=collection.get(newItem.id);if(oldItem)oldItem.set(newItem,options);else collection.add(newItem,options)});else collection.reset(newItems,options);delete attrs[key]}),Backbone.Model.prototype.set.call(self,attrs,options)},toJSON:function(){var self=this,json=Backbone.Model.prototype.toJSON.call(self);return _(self.relations).each(function(relation,key){var collection=self.attributes[key];json[key]=collection.toJSON()}),json},_onCollectionEvent:function(eventName){if(_(["change","add","remove"]).contains(eventName)){var options;if("change"==eventName)options=arguments[2];else options=arguments[3];this.trigger("change",this,options)}}});return HasCollectionsModel});