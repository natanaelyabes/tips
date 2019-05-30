export interface GraphPageHasPagination {
  pagination: boolean;
  currentPage: number;
  goToFirstPage(): void;
  goToPreviousPage(): void;
  goToPage(page: number): void;
  goToNextPage(): void;
  goToLastPage(): void;
}
