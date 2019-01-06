

$(function() {
    const environData = $('#environ-data').data();
    const text_surface = $('#textsurface');
    const update_button = $('#update');
    const input_rate = $('#rate');
    console.log("hi");
    let population = null;

    function disp_individual(x) {
        return "<p class='individual'>" +  x.join(" ") + "<p class='individual'>";
    }

    function show_population(pop) {
        text_surface.html("");
        population = pop;
        pop.forEach(
            function (x) {
                text_surface.append(disp_individual(x))
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