(function(document){
    "use strict";

    $(document).on("click", "coral-tab:last", function(event){

        let behavior = $("#behavior").find("coral-radio").filter("[checked]").attr("value");
        let numberOfElements = $("#numberOfElements").find("input").attr("aria-valuenow");
        let $multifield = $("#multifield");
        let $pathfield = $("#pathfield");

        if(behavior == "static"){
            $pathfield.attr("hidden", true);
            $pathfield.removeAttr("required");
            $multifield.removeAttr("hidden");
            addFields($multifield, numberOfElements)
            $pathfield.find("coral-tag").remove();
        } else {
            if(behavior == "dynamic"){
                $multifield.attr("hidden", true);
                $pathfield.removeAttr("hidden");
                $pathfield.attr("required", true);
                $multifield.find("coral-multifield-item").remove();
            }
        }
    });

    $(document).on("change", "#multifield:nth-child(1)", function(event){ 
        let addButton = $("#multifield").find("[coral-multifield-add]");
        if($("#multifield").find("coral-multifield-item").length < 
                    $("#numberOfElements").find("input").attr("aria-valuenow")) {
            addButton.show();
        } else {
            addButton.hide();
        }
    });
    
    $(document).on("change", "#numberOfElements", function (event) {
        $("#multifield").find("coral-multifield-item").remove();
    });

    function addFields(element, number){
        let addButton = element.find("[coral-multifield-add]");
        if (element.find("coral-multifield-item").length == 0) {
            for (let index = 1; index <= number; index++) {
                addButton.click();
            }     
            addButton.hide();              
        }
    }

})(document);
