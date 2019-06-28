/**
 * 编辑-InnoDB free: 206848 kBjs
 */
var vm = new Vue({
	el:'#dpLTE',
	data: {
		docArticle: {
			id: 0,
			content:null
		}
	},
	methods : {
		setForm: function() {
			$.SetForm({
				url: '../../doc/arc/info?_' + $.now(),
		    	param: vm.docArticle.id,
		    	success: function(data) {
		    		vm.docArticle = data;
		    	}
			});
		},
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.ConfirmForm({
		    	url: '../../doc/arc/update?_' + $.now(),
		    	param: vm.docArticle,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
});
