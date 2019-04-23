

$(function() {
    const environData = $('#environ-data').data();
    const text_surface = $('#textsurface');
    const update_button = $('#update');
    const input_rate = $('#rate');
    const params_form = $('#params');
    var gen_counter = 0;

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
        const restart_gens = $("#restart").val();
        if(restart_gens > 0 && gen_counter < restart_gens){
            if(params_form[0].checkValidity()) {
                $.post({
                    url: environData.generationurl,
                    data: JSON.stringify({'mutp': $("#mutp").val(), 'addp': $("#addp").val(), 'losep': $("#losep").val(), 'population': population}),
                    success: function (x) { show_population(x); },
                    dataType: "json",
                    contentType: 'application/json'});
                gen_counter++;
            }
        }
        else{
            $.get(environData.starturl, function (data) { show_population(data) } );
            gen_counter = 0;
        }
    }

    function change_params() {
        update_population();
        return false;
    }

    $.get(environData.starturl, function (data) { show_population(data) } );

    let evolutionary_process = window.setInterval(update_population, input_rate.val());

    params_form.submit(function(event) {
        window.clearInterval(evolutionary_process); 
        evolutionary_process = window.setInterval(update_population, input_rate.val()); 
        change_params();
    });

    update_button.click(update_population);
    $('#linger').click(function(event) { window.clearInterval(evolutionary_process); });


});