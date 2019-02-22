$(function() {
    const homofauxin_source = $("#homofauxin-source")
    const div_out = $("#out")

    function homofauxnighis() {
        $.get({
            url: "https://titleduntitled.name/homofauxin/",
            data: homofauxin_source.val(),
            success: function(x) { div_out.append(x); },
            dataType: "String",
            contentType: "String"
        });
    }

    $("#translate-button").click(homofauxnighis)
})