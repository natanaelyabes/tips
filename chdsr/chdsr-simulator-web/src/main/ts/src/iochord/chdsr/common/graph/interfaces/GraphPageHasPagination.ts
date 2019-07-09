/**
 *
 * @package chdsr
 * @author Natanael Yabes Wirawan <yabes.wirawan@gmail.com>
 * @since 2019
 *
 */
export interface GraphPageHasPagination {
  pagination: boolean;
  currentPage: number;
  goToFirstPage(): void;
  goToPreviousPage(): void;
  goToPage(page: number): void;
  goToNextPage(): void;
  goToLastPage(): void;
}
