

$(function() {
    const environData = $('#environ-data').data();
    const text_surface = $('#textsurface');
    const update_button = $('#update');
    const input_rate = $('#rate');
    console.log("hi");
    let population = null;

    function disp_individual(x, i) {
        return "<p class='individual' id='thing"+i+"'>" +  x[0].join(" ") + "<p class='individual'>";
    }

    function show_population(pop) {
        text_surface.html("");
        population = pop;
        i=0;
        pop.forEach(
            function (x) {
                i += 1;
                text_surface.append(disp_individual(x, i));
                $('#thing'+i).css("opacity", Math.min(1, 4*x[1]))
            }
        );
    }

    function update_population() {
        $.post({
            url: environData.generationurl,
            data: JSON.stringify({'population': population}),
            success: function (x) { show_population(x); },
            dataType: "json",
            contentType: 'application/json'});
    }

    $.get(environData.starturl, function (data) { show_population(data) } );

    let evolutionary_process = window.setInterval(update_population, input_rate.val());

    update_button.click(update_population);
    $('#advance').click(function() { window.clearInterval(evolutionary_process); evolutionary_process = window.setInterval(update_population, input_rate.val()); });
    $('#linger').click(function() { window.clearInterval(evolutionary_process) });


});