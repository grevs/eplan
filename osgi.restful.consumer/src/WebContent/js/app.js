(function() {
	  var app = angular.module('jsonTest', []);
	  
	  app.controller('ArticleController', function($scope, $http){
		    this.newArticle = {};
		    this.editedArticle = {};
			this.url = 'http://localhost:9595/services/Articles';
		    
			this.query = "";
			this.addArticle = function() {
					if($scope.addForm.$valid) {
						this.newArticle.id = 0;
						
						var req = {
								method: 'POST',
								url: this.url,
								headers: {
						          'Content-Type': 'application/json'
						        },
								data: this.newArticle
						}
						
						$http(req).success(angular.bind(this, function(data){
							$scope.articles.push(data);
							this.newArticle = {};
						}))
						.error(function(err){
							alert('POST error');
						});
					}
			 };
			 
			 this.editArticle = function(ind, id) {
				 this.editedArticle.id = id;	
				 
				 var req = {
						method: 'PUT',
						url: this.url,
						headers: {
				          'Content-Type': 'application/json'
				        },
						data: this.editedArticle
				 }
				 var index = ind;
				 
				$http(req).success(function(data){
					 if (index !== -1) {
						 $scope.articles[index] = data;
					 }
					 $scope.editedArticle = {};
				 })
				 .error(function(err){
					alert('POST error');
				 });
					
				 this.editedArticle = {};
			 };
			 
			 this.showEditForm = function(show) {
				 	$scope.showEdit = show;
			 };
			 
			 this.deleteArticle = function(id) {
				 var deleteUrl = this.url + '/'+id
				 var req = {
							method: 'DELETE',
							url: deleteUrl,
							headers: {
					          'Content-Type': 'text/plain'
					        }
						}
						
				$http(req).success(function(data){
					$scope.articles = $scope.articles.filter(function(article) {
					    return article.id !== id;
					});
				})
				.error(function(err){
					alert('DELETE error');
				});
			};
			 
			 
			 
			 this.fetchArticles = function() {
				 $http.get(this.url).success(function(data) {
						$scope.articles = data;
				 }).error(function(err) {
						alert('GET error');
				 });
			 };
			 
			 this.fetchArticles();
	  });
	  
})();
