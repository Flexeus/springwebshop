$(function () {
    $('.qtyminus').parent().children('.countfield').find('input').keydown(function () {
        // Save old value.
        if (!$(this).val() || (parseInt($(this).val()) <= $(this).attr('max') && parseInt($(this).val()) >= 1))
        $(this).data("old", $(this).val());
    });
    $('.qtyminus').parent().children('.countfield').find('input').keyup(function () {
    // Check correct, else revert back to old value.
        if (!$(this).val() || (parseInt($(this).val()) <= $(this).attr('max') && parseInt($(this).val()) >= 1))
        ;
        else
        $(this).val($(this).data("old"));
        postInfo($(this).attr('data-id'),$(this).val())
    });

    $('.qtyminus').click(function () {
            var $input = $(this).parent().children('.countfield').find('input');
            if (!$.isNumeric($input.val())){
                $input.val(1);
                $input.change();
            }
            if (parseInt($input.val()) <= 1) return false;
            $input.val(parseInt($input.val()) - 1);
            $input.change();
            postInfo($input.attr('data-id'),$input.val())
            return false;
        });

    $('.qtyplus').click(function () {
        var $input = $(this).parent().children('.countfield').find('input');
        if (!$.isNumeric($input.val())){
            $input.val(2);
            $input.change();
        }
        else if(parseInt($input.val()) <$input.attr('max')){
            $input.val(parseInt($input.val()) + 1);
            $input.change();
        }
        postInfo($input.attr('data-id'),$input.val())
        return false;
    });


    function postInfo(id,count){
        $.post("/cart/update",
        {
            productId: id,
            count: count
        },onAjaxSuccess);
    }

    function onAjaxSuccess(data)
    {

    }



});