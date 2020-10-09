$(document).ready(function () {
    var cardTable = $('#cards').DataTable({
        ajax: {
            url: "/card/list",
            type: "GET",
            dataSrc: ''
        },
        columns: [
            {title: "N", data: "id"},
            {title: "Number", data: "number"},
            {title: "Expire Date", data: "expireDate"},
            {title: "Bank", data: "bank"},
            {
                title: "Action", data: "id", render: function (data, type, row) {
                    return '<div class="row">\n' +
                        '        <div class="col">\n' +
                        '        <button data-toggle="modal" data-target="#modalDelete" class=" text-white btn btn-danger btn-block btn-delete" style="color: darkred"><i class="fab fa-bitbucket"> </i> Delete</button>\n' +
                        '    </div>\n' +
                        '</div>\n';
                }
            }
        ]
    });

    var cardForm = $('#cardForm');
    // var isCreate = true;

    $.validate({
        form: '#cardForm',
        errorMessageClass: 'text-danger',
        onSuccess: function ($form) {
            console.log(cardId);
            $.ajax({
                url:  "/card/add",
                contentType: "application/json; charset=utf-8",
                method: 'POST',
                dataType: 'json',
                data: JSON.stringify($form.serializeObject()),
                success: function (data) {
                    cardTable.ajax.reload();
                    $('#cardModal').modal('toggle');
                    if (data.success) {
                        $.toast({
                            heading: 'Information',
                            text: data.message,
                            icon: 'success',
                            position: 'top-right'
                        });
                    }else {
                        $.toast({
                            heading: 'information',
                            text: data.message,
                            icon: 'error',
                            position: 'top-right'
                        })
                    };
                },
                error: function () {
                    $.toast({
                        heading: 'information',
                        text: "Error",
                        icon: 'error',
                        position: 'top-right'
                    })
                }
            });
            return false;
        }
    });

    $('#addCard').click(function () {
        console.log($('#cardForm'));
        $('#cardForm')[0].reset();
    });

    var cardId = '';

    $(cardTable.table().body()).on('click', '.btn-delete', function () {
        var data = cardTable.row($(this).parents('tr')).data();
        cardId = data.id;
        $('#cardDeleteModal').modal('toggle');
    });

    $.fn.serializeObject = function () {
        var o = {};
        //    var a = this.serializeArray();
        $(this).find('input[type="hidden"], input[type="text"], input[type="password"], input[type="checkbox"]:checked, input[type="radio"]:checked, select, textarea').each(function () {
            if ($(this).attr('type') == 'hidden') { //if checkbox is checked do not take the hidden field
                var $parent = $(this).parent();
                var $chb = $parent.find('input[type="checkbox"][name="' + this.name.replace(/\[/g, '\[').replace(/\]/g, '\]') + '"]');
                if ($chb != null) {
                    if ($chb.prop('checked')) return;
                }
            }
            if (this.name === null || this.name === undefined || this.name === '')
                return;
            var elemValue = null;
            if ($(this).is('select'))
                elemValue = $(this).find('option:selected').val();
            else elemValue = this.value;
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(elemValue || '');
            } else {
                o[this.name] = elemValue || '';
            }
        });
        return o;
    }
    // $('#cardDelBtn').click(function () {
    //     $.ajax({
    //         url: "/card/delete/"+cardId,
    //         contentType: "application/json; charset=utf-8",
    //         method: 'DELETE',
    //         dataType: 'json',
    //         success:function (data) {
    //             $.toast({
    //                 heading: 'Information',
    //                 text: 'Successfully deleted.',
    //                 icon: 'success',
    //                 position: 'top-right'
    //             });
    //         },
    //         error: function () {
    //             $.toast({
    //                 heading: 'information',
    //                 text: 'Error in deleting.',
    //                 icon: 'error',
    //                 position: 'top-right'
    //             })
    //         }
    //     }).always(function () {
    //         cardTable.ajax.reload();
    //         $('#cardDeleteModal').modal('toggle');
    //     });
    // });


    $('#cardDelBtn').click(function () {
        $.ajax({
            url: "/card/delete/"+cardId,
            contentType: "application/json; charset=utf-8",
            method: 'DELETE',
            dataType: 'json',
            success:function (data) {
                if (data.success) {
                    $.toast({
                        heading: 'Information',
                        text: data.message,
                        icon: 'success',
                        position: 'top-right'
                    });
                }else {
                    $.toast({
                        heading: 'information',
                        text: data.message,
                        icon: 'error',
                        position: 'top-right'
                    })
                };
            },
            error: function () {
                $.toast({
                    heading: 'information',
                    text: 'Error in deleting.',
                    icon: 'error',
                    position: 'top-right'
                })
            }
        }).always(function () {
            cardTable.ajax.reload();
            $('#cardDeleteModal').modal('toggle');
        });
    });

});



// data-toggle="modal" data-target="#cardModal"