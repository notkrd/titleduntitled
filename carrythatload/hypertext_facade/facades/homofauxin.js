$(function() {
    const homofauxin_source = $("#homofauxin-source")
    const div_out = $("#out")

    function homofauxnighis() {
        $.getJSON({
            url: "https://titleduntitled.name/homofauxin/" + escape(homofauxin_source.val()),
            success: function(x) { div_out.append(x); },
            dataType: "String",
            contentType: "String"
        });
    }

    $("#translate-button").click(homofauxnighis)
})