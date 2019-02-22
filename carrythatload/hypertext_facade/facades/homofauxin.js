$(function() {
    const homofauxin_source = $("#homofauxin-source")
    const div_out = $("#out")

    function homofauxnighis() {
        $.get({
            url: "https://titleduntitled.name/homofauxin/" + escape(homofauxin_source.val()),
            success: function(x) { div_out.append("<p>" + x + "<\p>"); }
        });
    }

    $("#translate-button").click(homofauxnighis)
})