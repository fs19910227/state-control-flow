(function(e){function t(t){for(var a,s,i=t[0],l=t[1],c=t[2],m=0,d=[];m<i.length;m++)s=i[m],Object.prototype.hasOwnProperty.call(r,s)&&r[s]&&d.push(r[s][0]),r[s]=0;for(a in l)Object.prototype.hasOwnProperty.call(l,a)&&(e[a]=l[a]);u&&u(t);while(d.length)d.shift()();return o.push.apply(o,c||[]),n()}function n(){for(var e,t=0;t<o.length;t++){for(var n=o[t],a=!0,i=1;i<n.length;i++){var l=n[i];0!==r[l]&&(a=!1)}a&&(o.splice(t--,1),e=s(s.s=n[0]))}return e}var a={},r={app:0},o=[];function s(t){if(a[t])return a[t].exports;var n=a[t]={i:t,l:!1,exports:{}};return e[t].call(n.exports,n,n.exports,s),n.l=!0,n.exports}s.m=e,s.c=a,s.d=function(e,t,n){s.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:n})},s.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},s.t=function(e,t){if(1&t&&(e=s(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var n=Object.create(null);if(s.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var a in e)s.d(n,a,function(t){return e[t]}.bind(null,a));return n},s.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return s.d(t,"a",t),t},s.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},s.p="";var i=window["webpackJsonp"]=window["webpackJsonp"]||[],l=i.push.bind(i);i.push=t,i=i.slice();for(var c=0;c<i.length;c++)t(i[c]);var u=l;o.push([0,"chunk-vendors"]),n()})({0:function(e,t,n){e.exports=n("56d7")},"1ec3":function(e,t,n){"use strict";n("7d86")},"2b1c":function(e,t,n){"use strict";n("bc71")},"3b96":function(e,t,n){"use strict";n("e476")},4063:function(e,t,n){"use strict";n("ef14")},"56d7":function(e,t,n){"use strict";n.r(t);n("e260"),n("e6cf"),n("cca6"),n("a79d");var a=n("2b0e"),r=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("el-container",{attrs:{id:"app"}},[n("side-bar"),n("router-view")],1)},o=[],s=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("el-aside",{staticStyle:{"background-color":"rgb(238, 241, 246)"},attrs:{width:"240px"}},[n("el-menu",{attrs:{"default-openeds":["1","3"]}},[n("el-submenu",{attrs:{index:"1"}},[n("template",{slot:"title"},[n("i",{staticClass:"el-icon-menu"}),e._v("状态机列表")]),e._l(e.machines,(function(t,a){return[n("el-submenu",{key:a,attrs:{index:a}},[n("template",{slot:"title"},[e._v(e._s(a))]),e._l(t,(function(t){return[n("el-menu-item",{key:t.id,attrs:{id:t.id},on:{click:e.toMachine}},[e._v(" "+e._s(t.name)+" ")])]}))],2)]}))],2)],1)],1)},i=[],l=(n("b64b"),n("1da1")),c=(n("96cf"),n("bc3a")),u=n.n(c);u.a.defaults.baseURL="statemachine";var m={remoteUml:function(e){return Object(l["a"])(regeneratorRuntime.mark((function t(){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,u.a.get("http://www.plantuml.com/plantuml/png/".concat(e));case 2:return t.abrupt("return",t.sent);case 3:case"end":return t.stop()}}),t)})))()},metaList:function(){return Object(l["a"])(regeneratorRuntime.mark((function e(){var t;return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,u.a.get("/metaInfo/");case 2:return t=e.sent,e.abrupt("return",t.data);case 4:case"end":return e.stop()}}),e)})))()},states:function(e){return Object(l["a"])(regeneratorRuntime.mark((function t(){var n;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,u.a.get("/".concat(e,"/states"));case 2:return n=t.sent,t.abrupt("return",n.data);case 4:case"end":return t.stop()}}),t)})))()},events:function(e){return Object(l["a"])(regeneratorRuntime.mark((function t(){var n;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,u.a.get("/".concat(e,"/events"));case 2:return n=t.sent,t.abrupt("return",n.data);case 4:case"end":return t.stop()}}),t)})))()},metaDetail:function(e){return Object(l["a"])(regeneratorRuntime.mark((function t(){var n;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return t.next=2,u.a.get("/metaInfo/".concat(e));case 2:return n=t.sent,t.abrupt("return",n.data);case 4:case"end":return t.stop()}}),t)})))()},machineDetail:function(e,t){return Object(l["a"])(regeneratorRuntime.mark((function n(){var a;return regeneratorRuntime.wrap((function(n){while(1)switch(n.prev=n.next){case 0:return n.next=2,u.a.post("/detail/".concat(e),t);case 2:return a=n.sent,n.abrupt("return",a.data);case 4:case"end":return n.stop()}}),n)})))()},image:function(e,t){return Object(l["a"])(regeneratorRuntime.mark((function n(){var a;return regeneratorRuntime.wrap((function(n){while(1)switch(n.prev=n.next){case 0:return n.next=2,u.a.post("/".concat(e,"/uml/img"),t);case 2:return a=n.sent,n.abrupt("return",a.data);case 4:case"end":return n.stop()}}),n)})))()}},d=n("8c4f"),p=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("el-container",[n("el-main",{directives:[{name:"loading",rawName:"v-loading",value:e.state.loading,expression:"state.loading"}]},[n("el-row",[n("el-col",{attrs:{span:e.state.left}},[n("el-card",[n("el-form",{ref:"form",attrs:{model:e.state.form,"label-width":"80px"}},[n("el-form-item",{attrs:{label:"id"}},[n("el-input",{attrs:{disabled:!0},model:{value:e.state.form.id,callback:function(t){e.$set(e.state.form,"id",t)},expression:"state.form.id"}})],1),n("el-form-item",{attrs:{label:"名称"}},[n("el-input",{attrs:{disabled:!0},model:{value:e.state.form.name,callback:function(t){e.$set(e.state.form,"name",t)},expression:"state.form.name"}})],1),n("el-form-item",{attrs:{label:"详细描述"}},[n("el-input",{attrs:{disabled:!0,type:"textarea",autosize:{minRows:1,maxRows:5}},model:{value:e.state.form.desc,callback:function(t){e.$set(e.state.form,"desc",t)},expression:"state.form.desc"}})],1)],1)],1),n("el-card",[n("el-tabs",{attrs:{type:"border-card"}},[n("el-tab-pane",{attrs:{label:"状态列表"}},[n("el-card",[n("el-table",{staticStyle:{width:"100%"},attrs:{data:e.state.stateView.states}},e._l(e.state.stateView.heads,(function(e){return n("el-table-column",{key:e.label,attrs:{label:e.label,prop:e.propKey}})})),1)],1)],1),n("el-tab-pane",{attrs:{label:"事件列表"}},[n("el-card",[n("el-table",{staticStyle:{width:"100%"},attrs:{data:e.state.events}},[n("el-table-column",{attrs:{prop:"name",label:"name"}}),n("el-table-column",{attrs:{prop:"label",label:"label"}}),n("el-table-column",{attrs:{prop:"comments",label:"comments"}})],1)],1)],1),n("el-tab-pane",{attrs:{label:"Guard列表"}},[n("el-card",[n("el-table",{staticStyle:{width:"100%"},attrs:{data:e.state.guards}},[n("el-table-column",{attrs:{prop:"name",label:"名称"}}),n("el-table-column",{attrs:{prop:"desc",label:"描述"}}),n("el-table-column",{attrs:{prop:"guardClass",label:"类型"}})],1)],1)],1),n("el-tab-pane",{attrs:{label:"Action列表"}},[n("el-card",[n("el-table",{staticStyle:{width:"100%"},attrs:{data:e.state.actions}},[n("el-table-column",{attrs:{prop:"name",label:"名称"}}),n("el-table-column",{attrs:{prop:"desc",label:"描述"}}),n("el-table-column",{attrs:{prop:"actionClass",label:"类型"}})],1)],1)],1)],1)],1)],1),n("el-col",{attrs:{span:e.state.right}},[n("Uml")],1)],1)],1)],1)},f=[],b=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("el-card",[n("el-form",{ref:"form",attrs:{"label-width":"80px"}},[n("el-row",[n("el-col",{attrs:{span:12}},[n("el-row",[n("el-checkbox",{attrs:{label:"Show Internal"},on:{change:e.onConfig},model:{value:e.state.showInternal,callback:function(t){e.$set(e.state,"showInternal",t)},expression:"state.showInternal"}}),n("el-checkbox",{attrs:{label:"Show Acion"},on:{change:e.onConfig},model:{value:e.state.showAction,callback:function(t){e.$set(e.state,"showAction",t)},expression:"state.showAction"}}),n("el-checkbox",{attrs:{label:"Remote Render"},on:{change:e.changeRemote},model:{value:e.state.remoteRender,callback:function(t){e.$set(e.state,"remoteRender",t)},expression:"state.remoteRender"}})],1),n("el-row",[e._v(" EventMode "),n("el-select",{staticStyle:{width:"120px"},attrs:{size:"mini",placeholder:"请选择"},on:{change:e.onConfig},model:{value:e.state.eventViewMode,callback:function(t){e.$set(e.state,"eventViewMode",t)},expression:"state.eventViewMode"}},e._l(e.state.eventViewModes,(function(e){return n("el-option",{key:e,attrs:{label:e,value:e}})})),1)],1)],1),n("el-col",{attrs:{span:12}},[n("el-button",{attrs:{type:"primary",size:"mini"},on:{click:e.onMin}},[e._v(" 缩小 ")]),n("el-button",{attrs:{type:"primary",size:"mini"},on:{click:e.onMax}},[e._v(" 放大 ")])],1)],1)],1),n("el-image",{directives:[{name:"loading",rawName:"v-loading",value:e.state.imgLoading,expression:"state.imgLoading"}],attrs:{id:"uml",src:e.state.url,fit:"fill","preview-src-list":e.state.urls}})],1)},h=[],w={name:"UmlView",data:function(){return{state:this.$store.state.umlView}},methods:{onConfig:function(){var e=this;return Object(l["a"])(regeneratorRuntime.mark((function t(){return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:e.$store.commit("refreshUml");case 1:case"end":return t.stop()}}),t)})))()},changeRemote:function(e){this.state.remoteRender=e},onMin:function(){this.$store.commit("onMin")},onMax:function(){this.$store.commit("onMax")}}},g=w,v=(n("1ec3"),n("2877")),x=Object(v["a"])(g,b,h,!1,null,"32b66940",null),y=x.exports,R={components:{Uml:y},name:"Detail",data:function(){return{state:this.$store.state.detail}},created:function(){this.fetchData()},watch:{$route:"fetchData"},computed:{},methods:{fetchData:function(){this.$store.commit("refreshDetail",this.$route.params.id)}}},k=R,O=(n("2b1c"),Object(v["a"])(k,p,f,!1,null,"443136b9",null)),j=O.exports;a["default"].use(d["a"]);var _=[{name:"detail",path:"/detail/:id",component:j}],M=new d["a"]({routes:_}),V=M,$={name:"SideBar",data:function(){return{machines:{}}},methods:{initData:function(e){this.machines=e;var t=e[Object.keys(e)[0]][0].id;console.log(e);var n=this.$route.params.id?this.$route.params.id:t;V.push({name:"detail",params:{id:n}})},toMachine:function(e){V.push({name:"detail",params:{id:e.$attrs.id}})}},mounted:function(){var e=this;m.metaList().then((function(t){return e.initData(t)}))}},S=$,L=(n("4063"),Object(v["a"])(S,s,i,!1,null,"2844187e",null)),D=L.exports,I={components:{SideBar:D},name:"app"},C=I,A=(n("3b96"),Object(v["a"])(C,r,o,!1,null,"812019ee",null)),P=A.exports,U=n("5c96"),E=n.n(U);n("0fae");a["default"].use(E.a);n("aede"),n("d81d"),n("b0c0");var z=n("2f62");function T(e,t){return B.apply(this,arguments)}function B(){return B=Object(l["a"])(regeneratorRuntime.mark((function e(t,n){return regeneratorRuntime.wrap((function(e){while(1)switch(e.prev=e.next){case 0:n.umlImg,t.remoteRender?t.url="http://www.plantuml.com/plantuml/png/".concat(n.umlCode):t.url=n.umlImg,t.urls=[t.url],t.umlCode=n.umlCode,t.showInternal=n.showInternal,t.showAction=n.showAction,t.eventViewMode=n.eventViewMode,t.eventViewModes=n.eventViewModes;case 8:case"end":return e.stop()}}),e)}))),B.apply(this,arguments)}a["default"].use(z["a"]);var J=new z["a"].Store({state:{umlView:{url:"none",umlCode:"",urls:[],showInternal:!1,showAction:!0,remoteRender:!1,eventViewMode:"ALL",eventViewModes:["ALL"],imgLoading:!1},detail:{form:{id:"id",name:"name",desc:"desc"},left:8,right:16,stateView:{},events:[],guards:[],actions:[],loading:!0}},mutations:{refreshDetail:function(e,t){return Object(l["a"])(regeneratorRuntime.mark((function n(){var a,r,o;return regeneratorRuntime.wrap((function(n){while(1)switch(n.prev=n.next){case 0:return a=e.detail,a.loading=!0,n.next=4,m.machineDetail(t,e.umlView);case 4:r=n.sent,T(e.umlView,r.umlView),o=r.eventDescList,a.form=r.metaInfo,a.guards=r.guardDescList,a.actions=r.actionDescList,a.stateView=r.stateView,a.events=o.map((function(e){return{name:e.name,label:e.label,comments:e.comments}})),a.loading=!1;case 13:case"end":return n.stop()}}),n)})))()},refreshUml:function(e){return Object(l["a"])(regeneratorRuntime.mark((function t(){var n;return regeneratorRuntime.wrap((function(t){while(1)switch(t.prev=t.next){case 0:return e.umlView.imgLoading=!0,t.next=3,m.image(e.detail.form.id,e.umlView);case 3:n=t.sent,T(e.umlView,n),e.umlView.imgLoading=!1;case 6:case"end":return t.stop()}}),t)})))()},onMin:function(e){var t=e.detail;t.right<=6||(t.right-=2,t.left+=2)},onMax:function(e){var t=e.detail;t.right>=24||(t.right+=2,t.left-=2)}},actions:{},modules:{}});a["default"].config.productionTip=!1,new a["default"]({router:V,store:J,render:function(e){return e(P)}}).$mount("#app")},"7d86":function(e,t,n){},aede:function(e,t,n){},bc71:function(e,t,n){},e476:function(e,t,n){},ef14:function(e,t,n){}});
//# sourceMappingURL=app.3e40e0f4.js.map