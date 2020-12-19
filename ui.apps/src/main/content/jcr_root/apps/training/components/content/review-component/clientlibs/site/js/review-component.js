// review-component.base.js

$(function(){
    "use strict";

    let portion = new Array();
    let button = $("#button");
    button.on("click", function(event){
        let existingPages = new Array();
        $(".review-component__minipage").find("a").each(function () {
	        existingPages.push($(this).attr("href"));
        });
        $.ajax("http://localhost:4502/content/training-home/jcr:content/content/review_component.data.html", {
            data : {
                pathsToExistingPages : JSON.stringify(existingPages)
            },            
            success(data){
                portion = data.portion;
                renderMinipages(portion);
				if(data.remainder <= 0){
                    button.hide();
                }            
        	},
            error(){
                alert("error with request");
            }
        });

    });

    function renderMinipages(portion) {
        for (let i = 0; i < portion.length; i++) {
            $('<div class="review-component__minipage">' +
            '<a href="' + portion[i].referenceToPage +'"><img class="review-component__image" ' + 
            'src="' + portion[i].pathToImage + '"></a>' + 
            '<h2>' + portion[i].title + '</h2>' + 
            portion[i].text + '</div>')
            .insertBefore("form");
        } 
    }
});
