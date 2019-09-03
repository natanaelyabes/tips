(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chdsr-simulation-editor-ui-view"],{e122:function(t,i,e){"use strict";e.r(i);var a=function(){var t=this,i=t.$createElement,e=t._self._c||i;return e("div",{staticClass:"simulation editor test view"},[t._m(0),e("StartNodeModal"),e("StopNodeModal"),e("ActivityNodeModal"),e("ObjectTypeDataModal"),e("GeneratorDataModal"),e("FunctionDataModal"),e("QueueDataModal"),e("ResourceDataModal"),e("BranchNodeModal"),e("ConfigurationModal"),e("ControlModal")],1)},s=[function(){var t=this,i=t.$createElement,e=t._self._c||i;return e("div",{staticClass:"ui basic segment"},[e("h1",[t._v("Simulation Editor Test")]),e("div",{attrs:{id:"mypaper"}})])}],l=e("d225"),o=e("b0b4"),r=e("308d"),d=e("6bb5"),n=e("4e2b"),c=e("9ab4"),b=e("60a3"),y=e("de6d"),v=(e("1646"),function(t){function i(){var t;return Object(l["a"])(this,i),t=Object(r["a"])(this,Object(d["a"])(i).apply(this,arguments)),t.selectedGate="",t.selectedSJ="",t.selectedRule="",t.selectedActivityType="",t}return Object(n["a"])(i,t),Object(o["a"])(i,[{key:"updated",value:function(){"xor"===this.selectedGate&&"split"===this.selectedSJ?($("#row_branches_rule").attr("style","visibility:visible"),$("#row_branches_if").attr("style","visibility:visible"),$("#row_branches_tbl").attr("style","visibility:visible")):($("#row_branches_rule").attr("style","visibility:hidden"),$("#row_branches_if").attr("style","visibility:hidden"),$("#row_branches_tbl").attr("style","visibility:hidden")),"standard"===this.selectedActivityType?($("#basic-standard-sm-1").attr("style","visibility:visible"),$("#basic-cbp-sm-1").attr("style","visibility:hidden"),$("#basic-cbp-sm-2").attr("style","visibility:hidden"),$("#basic-cbp-sm-3").attr("style","visibility:hidden"),$("#basic-split-sm-1").attr("style","visibility:hidden"),$("#basic-split-sm-2").attr("style","visibility:hidden"),$("#basic-split-sm-3").attr("style","visibility:hidden"),$("#basic-split-sm-3").attr("style","visibility:hidden")):"concurrent"===this.selectedActivityType?($("#basic-standard-sm-1").attr("style","visibility:hidden"),$("#basic-cbp-sm-1").attr("style","visibility:visible"),$("#basic-cbp-sm-2").attr("style","visibility:visible"),$("#basic-cbp-sm-3").attr("style","visibility:visible"),$("#basic-split-sm-1").attr("style","visibility:hidden"),$("#basic-split-sm-2").attr("style","visibility:hidden"),$("#basic-split-sm-3").attr("style","visibility:hidden"),$("#basic-split-sm-3").attr("style","visibility:hidden")):($("#basic-standard-sm-1").attr("style","visibility:hidden"),$("#basic-cbp-sm-1").attr("style","visibility:hidden"),$("#basic-cbp-sm-2").attr("style","visibility:hidden"),$("#basic-cbp-sm-3").attr("style","visibility:hidden"),$("#basic-split-sm-1").attr("style","visibility:visible"),$("#basic-split-sm-2").attr("style","visibility:visible"),$("#basic-split-sm-3").attr("style","visibility:visible"),$("#basic-split-sm-4").attr("style","visibility:visible"))}},{key:"mounted",value:function(){var t=this;$(".ui.dropdown").dropdown(),$(".tabular.menu .item").tab(),$("#btn_add_rule").click(function(){var t="<tr>";t+='<td>Case.loan > 5000 and case.bank = "A"</td>',t+="<td>",t+='<button id="btn_del_rule" class="ui negative basic button">',t+='<i class="close icon"></i>',t+="</button>",t+="</td>",t+="</tr>",$("#tb_add_row").append(t)}),$("#btn_del_rule").click(function(){}),$("#row_branches_tbl").on("click","#btn_del_rule",function(){$("#btn_del_rule").closest("tr").remove()}),$("#sim_config_generate").click(function(){var t=$("#rsc_txt").val(),i="";$("#sim_config_tbl").html(i);for(var e="",a="",s=t,l=0;l<=s;l++)e+="<th>Config "+(l+1)+"</th>",a+='<td><button class="ui button">...</button></td>';i+='<table class="ui celled compact table">',i+="<thead>",i+="<tr>",i+="<th>Module</th>",i+=e,i+="</tr>",i+="</thead>",i+="<tbody>",i+="<tr>",i+="<td>Start</td>",i+=a,i+="</tr>",i+="<tr>",i+="<td>End</td>",i+=a,i+="</tr>",i+="</tbody>",i+="</table>",$("#sim_config_tbl").html(i)});var i=new y["dia"].Graph,e=new y["dia"].Paper({el:document.getElementById("mypaper"),model:i,width:window.innerWidth,height:window.innerHeight,gridSize:1}),a=y["shapes"].standard,s=new a.Circle;s.resize(20,20),s.position(100,50),s.attr("root/title","start"),s.attr("body/fill","white"),s.addTo(i);var l=new a.Rectangle;l.resize(50,50),l.position(250,25),l.attr({rect:{transform:"rotate(45)"}}),l.attr("root/title","branch"),l.attr("body/fill","white"),l.addTo(i);var o=new a.Rectangle;o.resize(100,50),o.position(400,35),o.attr("root/title","activity"),o.attr("body/fill","white"),o.addTo(i);var r=o.clone();r.position(400,150),r.addTo(i);var d=l.clone();d.position(700,25),d.addTo(i);var n=s.clone();n.position(800,50),n.attr("root/title","stop"),n.attr("body/fill","black"),n.addTo(i);var c=new y["dia"].Link;c.attr("root/title","link"),c.source(s),c.target(l),c.addTo(i);var b=new y["dia"].Link;b.source(l),b.target(o),b.addTo(i);var v=new y["dia"].Link;v.source(l),v.target(r),v.addTo(i);var u=new y["dia"].Link;u.source(o),u.target(d),u.addTo(i);var m=new y["dia"].Link;m.source(r),m.target(d),m.addTo(i);var h=new y["dia"].Link;h.source(d),h.target(n),h.addTo(i);var p=new a.Rectangle;p.resize(100,50),p.position(400,250),p.attr("root/title","object_type"),p.attr("body/fill","white"),p.attr("label/text","Object Type"),p.addTo(i);var w=p.clone();w.position(100,100),w.resize(40,40),w.attr("body/fill","lightblue"),w.attr("root/title","generator"),w.attr("label/text","G"),w.addTo(i);var _=0,f=p.clone();function g(t){for(var i=t.model.getElements(),e=0,a=i.length;e<a;e++){var s=i[e];s.attr("body/stroke","black")}for(var l=t.model.getLinks(),o=0,r=l.length;o<r;o++){var d=l[o];d.attr("line/stroke","black"),d.label(0,{attrs:{body:{stroke:"black"}}})}}f.resize(200,50),f.position(400,320),f.attr("root/title","general"),f.attr("label/text","Other Forms"),f.addTo(i),e.on({"blank:pointerclick":function(i){g(t)},"element:contextmenu":function(i){g(t);var e=i.model;e.attr("body/stroke","red");var a=e.attr("root/title");"start"===a?($("#start_modal_title").html("<b>"+a+"</b>"),$(".ui.tiny.start.modal").modal("show")):"stop"===a?($("#stop_modal_title").html("<b>"+a+"</b>"),$(".ui.tiny.stop.modal").modal("show")):"activity"===a?$(".ui.tiny.activity.modal").modal("show"):"object_type"===a?$(".ui.tiny.object_type.modal").modal("show"):"generator"===a?$(".ui.tiny.generator.modal").modal("show"):"link"===a||("branch"===a?$(".ui.tiny.branches.modal").modal("show"):"general"===a&&(0===_?$(".ui.tiny.queue.modal").modal("show"):1===_?$(".ui.tiny.resource.modal").modal("show"):2===_||(3===_?$(".ui.large.sim_config.modal").modal("show"):4===_?$(".ui.tiny.sim_control.modal").modal("show"):_=-1),_++))},"link:contextmenu":function(t){t.model;$(".ui.tiny.function.modal").modal("show"),_++}})}}]),i}(b["d"]));v=c["b"]([b["a"]],v);var u=v,m=u,h=(e("eaff"),e("2877")),p=Object(h["a"])(m,a,s,!1,null,null,null);i["default"]=p.exports},eaff:function(t,i,e){"use strict";var a=e("f211"),s=e.n(a);s.a},f211:function(t,i,e){}}]);
//# sourceMappingURL=chdsr-simulation-editor-ui-view.c36c17ae.js.map