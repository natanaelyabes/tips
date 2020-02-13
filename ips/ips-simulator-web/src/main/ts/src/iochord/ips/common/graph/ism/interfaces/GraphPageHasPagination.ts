/**
 * Allow graph to have pagination. Provides a way to navigate from one page to another within the graph object.
 *
 * @export
 * @interface GraphPageHasPagination
 *
 * @package ips
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface GraphPageHasPagination {

  /**
   * Activate pagination of graph.
   *
   * @type {boolean}
   * @memberof GraphPageHasPagination
   */
  pagination: boolean;

  /**
   * Current index of active page.
   *
   * @type {number}
   * @memberof GraphPageHasPagination
   */
  currentPage: number;

  /**
   * Navigate to the first page of the graph.
   *
   * @memberof GraphPageHasPagination
   */
  goToFirstPage(): void;

  /**
   * Navigate to one step previous from the current index.
   *
   * @memberof GraphPageHasPagination
   */
  goToPreviousPage(): void;

  /**
   * Navigate to specfied index of the page.
   *
   * @param {number} page
   * @memberof GraphPageHasPagination
   */
  goToPage(page: number): void;

  /**
   * Navigate to one step next from the current index.
   *
   * @memberof GraphPageHasPagination
   */
  goToNextPage(): void;

  /**
   * Navigate to the last page of the page.
   *
   * @memberof GraphPageHasPagination
   */
  goToLastPage(): void;
}
