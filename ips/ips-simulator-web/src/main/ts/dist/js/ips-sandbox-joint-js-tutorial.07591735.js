(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["ips-sandbox-joint-js-tutorial"],{"24c4":function(e,t,n){"use strict";n.r(t);var a=function(){var e=this,t=e.$createElement;e._self._c;return e._m(0)},r=[function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{staticClass:"sandbox joint js view"},[n("div",{attrs:{id:"canvas"}})])}],i=n("b0b4"),c=n("d225"),o=n("308d"),s=n("6bb5"),u=n("4e2b"),l=n("9ab4"),d=n("60a3"),b=n("c60e"),f=n("de6d"),p=(n("1646"),function(e){function t(){var e;return Object(c["a"])(this,t),e=Object(o["a"])(this,Object(s["a"])(t).call(this)),e.attr(".label/text","On"),e.attr(".root/fill","lightgreen"),e.attr(".root/stroke","black"),e}return Object(u["a"])(t,e),t}(f["shapes"].pn.Place)),h=function(e){function t(){var e;return Object(c["a"])(this,t),e=Object(o["a"])(this,Object(s["a"])(t).call(this)),e.attr(".label/text","Off"),e.attr(".root/fill","red"),e.attr(".root/stroke","black"),e}return Object(u["a"])(t,e),t}(f["shapes"].pn.Place),O=function(e){function t(e){var n;return Object(c["a"])(this,t),n=Object(o["a"])(this,Object(s["a"])(t).call(this,e)),n.drawBackground({color:"#fad390"}),n}return Object(u["a"])(t,e),t}(f["dia"].Paper),j=function(e){function t(){return Object(c["a"])(this,t),Object(o["a"])(this,Object(s["a"])(t).apply(this,arguments))}return Object(u["a"])(t,e),Object(i["a"])(t,[{key:"overrideBrowserProperties",value:function(){this.setDocumentTitle("Joint.js Tutorial")}},{key:"mounted",value:function(){var e=new f["dia"].Graph,t=new f["dia"].Paper({el:$("#canvas"),model:e,width:window.innerWidth,height:$("#canvas").innerHeight(),gridSize:10,drawGrid:!0}),n=new f["shapes"].pn.Place;n.position(100,100),n.addTo(e);var a=new f["shapes"].pn.Transition;a.position(220,100),a.attr(".label/text","Turn Off"),a.addTo(e);var r=new f["shapes"].pn.Place;r.position(300,100),r.addTo(e);var i=new f["shapes"].pn.Link;i.source(n),i.target(a),i.addTo(e);var c=new f["shapes"].pn.Link;c.source(a),c.target(r),c.addTo(e);var o=f["V"]("circle",{r:7,fill:"orange",stroke:"black"});i.findView(t).sendToken(o,{duration:1e4},function(){console.log("animation end")})}}]),t}(b["a"]);j=l["b"]([d["a"]],j);var v=j,w=v,k=(n("59dd"),n("2877"));n.d(t,"OnPlace",function(){return p}),n.d(t,"OffPlace",function(){return h}),n.d(t,"DesertThemePaper",function(){return O});var P=Object(k["a"])(w,a,r,!1,null,null,null);t["default"]=P.exports},"59dd":function(e,t,n){"use strict";var a=n("f0ba"),r=n.n(a);r.a},"62e4":function(e,t){e.exports=function(e){return e.webpackPolyfill||(e.deprecate=function(){},e.paths=[],e.children||(e.children=[]),Object.defineProperty(e,"loaded",{enumerable:!0,get:function(){return e.l}}),Object.defineProperty(e,"id",{enumerable:!0,get:function(){return e.i}}),e.webpackPolyfill=1),e}},c60e:function(e,t,n){"use strict";var a=n("d225"),r=n("b0b4"),i=n("308d"),c=n("6bb5"),o=n("4e2b"),s=n("9ab4"),u=n("3bf0"),l=n("3916"),d=n("60a3"),b=function(e){function t(){return Object(a["a"])(this,t),Object(i["a"])(this,Object(c["a"])(t).apply(this,arguments))}return Object(o["a"])(t,e),Object(r["a"])(t,[{key:"mounted",value:function(){this.overrideBrowserProperties()}},{key:"overrideBrowserProperties",value:function(){this.setDocumentTitle("Untitled")}},{key:"setDocumentTitle",value:function(e){document.title="".concat(l["b"].IOCHORD,"/").concat(l["a"].NAME.toUpperCase()," · ").concat(e)}}]),t}(u["a"]);b=s["b"]([d["a"]],b),t["a"]=b},f0ba:function(e,t,n){}}]);
//# sourceMappingURL=ips-sandbox-joint-js-tutorial.07591735.js.map