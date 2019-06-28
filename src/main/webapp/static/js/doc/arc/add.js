/**
 * 新增-InnoDB free: 206848 kBjs
 */
var vm = new Vue({
	el:'#dpLTE',
	data: {
		docArticle: {
			id: 0,
			content:null
		},
		htmlResult: '',
		textResult: ''
	},
	methods : {
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.SaveForm({
		    	url: '../../doc/arc/save?_' + $.now(),
		    	param: vm.docArticle,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}

	}
})