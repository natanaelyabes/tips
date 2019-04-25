export interface GraphPageHasPagination {
  pagination: boolean;
  currentPage: number;
  fn_graph_page_go_to_first(): void;
  fn_graph_page_go_to_previous(): void;
  fn_graph_page_go_to(page: number): void;
  fn_graph_page_go_to_next(): void;
  fn_graph_page_go_to_last(): void;
}
