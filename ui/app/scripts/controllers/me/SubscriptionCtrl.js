'use strict';

/*global app: false */

/**
 * The clazz controller.
 *
 */
app.controller('SubscriptionCtrl', ['$modal', '$scope', 'ModalService', function($modal, $scope,ModalService) {

  $scope.animationsEnabled = true;
  $scope.items = ['item1', 'item2', 'item3'];
  $scope.selected = {
    item: $scope.items[0]
  };

  /*
  $scope.open = function (size) {

    $scope.$modalInstance = $modal.open({
      animation: true,
      templateUrl: 'myModalContent.html',
      size: size,
      scope: $scope,
      resolve: {
        items: function () {
          return $scope.items;
        }
      }
    });

    console.log($scope.$modalInstance);


    $scope.$modalInstance.result.then(function (selectedItem) {
      console.log('closed :'+selectedItem);
      $scope.selected = selectedItem;
    }, function () {
      console.log('canceled');
    });
  };

  $scope.ok = function() {
    $scope.$modalInstance.close($scope.selected.item);
  };

  $scope.cancel = function() {
    $scope.$modalInstance.dismiss('cancel');
  };
  */

  $scope.delete = function() {
    var modalOptions = {
      closeButtonText: 'Cancel',
      actionButtonText: 'Delete',
      headerText: 'Delete?',
      bodyText: 'Are you sure you want to delete this subs?'
    };

    ModalService.showModal({}, modalOptions).then(function (result) {
      if (result === 'ok') {
        console.log("OKOKOK")
      }
    });

  };

}]);




