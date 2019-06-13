<template>
    <div class="simulation editor kpi view">
        <div class="ui basic segment">
            <h1>JointJS KPI Testing</h1>
            <div id="myholder"></div>
        </div>

        <div class="ui mini modal">
            <div id="min_mdl_el" class="header">
                Setting 
            </div>
            <div class="content">
                <div class="ui form">
                    <div class="inline field">
                        <label>Label:</label>
                        <input type="text" id="el_label" placeholder="Label">                        
                    </div>
                    <div class="inline field">
                        <label>Height:</label>
                        <input type="text" id="el_height" placeholder="height">                        
                    </div>
                    <div class="inline field">
                        <label>Width:</label>
                        <input type="text" id="el_width" placeholder="width">
                    </div>
                </div>
            </div>
            <div class="actions">
                <div class="ui approve button">Approve</div>
            </div>
        </div>
    </div>
</template>

<style>

</style>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import * as joint from 'jointjs';
import '#root/node_modules/jointjs/dist/joint.css';

declare const $: any;

@Component
export default class SimulationEditorKpiView extends Vue {
    private mounted(): void {
        const warna = ['blue', 'red']

        const graph = new joint.dia.Graph;

        const paper = new joint.dia.Paper({
            el: document.getElementById('myholder'),
            model: graph,
            width: window.innerWidth,
            height: window.innerHeight,
            gridSize: 1,
        });

        paper.on({
            'element:contextmenu': function(elementView){
                const currentElement = elementView.model;
                //alert("Element Attr: "+currentElement.attr("label/text"));
                $('#min_mdl_el').html("Setting "+currentElement.attr("label/text"));
                $('#el_label').val(currentElement.attr("label/text"));

                $('.ui.mini.modal').modal("setting",{
                    onApprove: function(){
                        var width = $('#el_width').val();
                        var height = $('#el_height').val();
                        var label = $('#el_label').val();
                        
                        currentElement.attr("label/text",label);
                        currentElement.resize(width, height);
                        
                        return true;                        
                    }
                }).modal('show');
            },

            'blank:pointerdblclick' : function(evt, x, y){
                const kotak = new joint.shapes.standard.Rectangle();
                kotak.position(x, y);
                kotak.resize(100, 50);
                kotak.attr({
                    body: {
                        fill: warna[Math.floor((Math.random()*warna.length)+1)]
                    },
                    label:{
                        text: 'Kotak Baru',
                        fill: 'black'
                    }
                });

                kotak.addTo(graph);
            },
            
            'element:pointerdblclick' : function(elementView){
                const currentElement = elementView.model;
                currentElement.attr('label/text', 'Di click bro');
            }
        });

        const rect = new joint.shapes.standard.Rectangle();
        rect.position(100, 30);
        rect.resize(100, 40);
        rect.attr({
            body: {
                fill: 'blue'
            },
            label: {
                text: 'Hello',
                fill: 'white'
            }
        });
        rect.addTo(graph);

        const rect2 = rect.clone() as joint.shapes.standard.Rectangle;
        //rect2.translate(300, 0);
        rect2.position(300,60);
        rect2.attr('label/text', 'World!');
        rect2.addTo(graph);

        const link = new joint.shapes.standard.Link();
        link.source(rect);
        link.target(rect2);
        link.addTo(graph);
    }
}
</script>
