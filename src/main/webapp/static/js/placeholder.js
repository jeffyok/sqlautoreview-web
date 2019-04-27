/**
 * 
 */
(function($) {
	$.fn.placeholder = function() {
		// 判断文本框中的值是否和placeholder中的值一样
		// 如果一样，则返回true
		function valueIsPlaceholder(input) {
			return ($(input).val() == $(input).attr("placeholder"));
		}
		return this.each(function() {
			$(this).find(":input").each(
			function() {
				if ($(this).attr("type") == "password") {
					var new_field = $("");
					new_field.attr("rel", $(this).attr(
							"id"));
					new_field.attr("value", $(this)
							.attr("placeholder"));
					$(this).parent().append(new_field);
					new_field.hide();

					function showPasswordPlaceholer(
							input) {
						if ($(input).val() == ""
								|| valueIsPlaceholder(input)) {
							$(input).hide();
							$(
									"input[rel="
											+ $(input)
													.attr(
															'id')
											+ "]")
									.show();
						}
					}

					new_field.focus(function() {
						$(this).hide();
						$(
								'input#'
										+ $(this).attr(
												"rel"))
								.show().focus();
					});

					$(this).blur(function() {
						showPasswordPlaceholer(this);
					});

					showPasswordPlaceholer(this);
				} else {
					function showPlaceholder(input,
							reload) {
						if ($(input).val() == ""
								|| (reload && valueIsPlaceholder(input))) {
							$(input)
									.val(
											$(input)
													.attr(
															"placeholder"));
						}
					}

					$(this).focus(function() {
						if (valueIsPlaceholder(this)) {
							$(this).val('');
						}
					});

					$(this).blur(function() {
						showPlaceholder(this, false);
					});

					showPlaceholder(this, true);
				}
			});

		});
	}
})(jQuery);